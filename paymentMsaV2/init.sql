-- CREATE USER 'kiwi'@'%' IDENTIFIED BY '1234';
-- GRANT ALL ON *.* TO 'kiwi'@'%';

create schema payment_msa_v2_order;
create schema payment_msa_v2_catalog;
create schema payment_msa_v2_wallet;
create schema payment_msa_v2_catalog_review;
create schema payment_msa_v2_catalog_like;
create schema payment_msa_v2_seller;

create table payment_msa_v2_order.payment_events
(
    payment_event_no bigint                               not null
        primary key,
    member_no        bigint                               null,
    is_payment_done  tinyint(1) default 0                 not null,
    is_wallet_done   tinyint(1) default 0                 not null,
    payment_key      varchar(255)                         null,
    order_id         varchar(255)                         null,
    type             enum ('NORMAL')                      not null,
    order_name       varchar(255)                         null,
    method           enum ('CARD')                        null,
    approved_at      datetime                             null,
    created_at       datetime    not null,
    updated_at       datetime    not null,
    constraint order_id
        unique (order_id),
    constraint payment_key
        unique (payment_key)
);

create table payment_msa_v2_order.payment_orders
(
    payment_order_no     bigint                                                                                       not null
        primary key,
    payment_event_no     bigint                                                                                       not null,
    product_no           bigint                                                                                       not null,
    seller_no            bigint                                                                                       null,
    order_id             varchar(255)                                                                                 not null,
    product_name         varchar(100)                                                                                 null,
    amount               decimal(12, 2)                                                                               not null,
    payment_order_status enum ('NOT_STARTED', 'EXECUTING', 'SUCCESS', 'FAILURE', 'UNKNOWN') default 'NOT_STARTED'     not null,
    failed_count         tinyint                                                            default 0                 not null,
    threshold            tinyint                                                            default 5                 not null,
    created_at           datetime                                                           not null,
    updated_at           datetime                                                         not null
);

create table payment_msa_v2_order.payment_order_histories
(
    payment_order_history_no bigint                                                             not null
        primary key,
    payment_order_no         bigint                                                             not null,
    previous_status          enum ('NOT_STARTED', 'EXECUTING', 'SUCCESS', 'FAILURE', 'UNKNOWN') null,
    new_status               enum ('NOT_STARTED', 'EXECUTING', 'SUCCESS', 'FAILURE', 'UNKNOWN') null,
    created_at               datetime                            not null,
    updated_at               datetime                              not null,
    changed_by               varchar(255)                                                       null,
    reason                   varchar(255)                                                       null
);

create table payment_msa_v2_order.outbox
(
    outbox_no       bigint                              not null
        primary key,
    shard_key       bigint                              not null,
    event_type      varchar(100)                        not null,
    idempotency_key varchar(128)                        null,
    status          enum ('INIT', 'FAILURE', 'SUCCESS') not null,
    type            varchar(50)                         null,
    partition_key   int                                 null,
    payload         longtext                            null,
    metadata        longtext                            null,
    created_at      datetime   not null,
    updated_at      datetime   not null,
    constraint idempotency_key
        unique (idempotency_key)
);

create table payment_msa_v2_catalog.products
(
    product_no bigint                             not null
        primary key,
    seller_no  bigint                             null,
    product_id varchar(255)                       not null,
    name       varchar(50)                        not null,
    price      decimal(12, 2)                     not null,
    created_at datetime not null,
    updated_at datetime not null
);

create table payment_msa_v2_catalog.outbox
(
    outbox_no       bigint                              not null
        primary key,
    shard_key       bigint                              not null,
    event_type      varchar(100)                        not null,
    idempotency_key varchar(128)                        null,
    status          enum ('INIT', 'FAILURE', 'SUCCESS') not null,
    type            varchar(50)                         null,
    partition_key   int                                 null,
    payload         longtext                            null,
    metadata        longtext                            null,
    created_at      datetime   not null,
    updated_at      datetime   not null,
    constraint idempotency_key
        unique (idempotency_key)
);

create table payment_msa_v2_catalog_like.product_likes
(
    product_like_no bigint   not null
        primary key,
    product_no      bigint   not null,
    member_no       bigint   not null,
    created_at      datetime not null,
    updated_at      datetime null,
    constraint idx_product_no_member_no
        unique (product_no, member_no)
);

create table payment_msa_v2_catalog_like.product_like_counts
(
    product_no bigint                             not null
        primary key,
    like_count bigint                             not null,
    version    bigint                             not null,
    created_at datetime  not null,
    updated_at datetime  not null
);

create table payment_msa_v2_catalog_like.outbox
(
    outbox_no       bigint                              not null
        primary key,
    shard_key       bigint                              not null,
    event_type      varchar(100)                        not null,
    idempotency_key varchar(128)                        null,
    status          enum ('INIT', 'FAILURE', 'SUCCESS') not null,
    type            varchar(50)                         null,
    partition_key   int                                 null,
    payload         longtext                            null,
    metadata        longtext                            null,
    created_at      datetime not null,
    updated_at      datetime  not null,
    constraint idempotency_key
        unique (idempotency_key)
);


create table payment_msa_v2_catalog_review.product_reviews
(
    product_review_no bigint                             not null
        primary key,
    product_no        bigint                             not null,
    member_no         bigint                             not null,
    title             varchar(100)                       not null,
    content           varchar(3000)                      not null,
    star_rating       bigint                             not null,
    is_deleted        tinyint(1)                         not null,
    created_at        datetime  not null,
    updated_at        datetime not null
);

create table payment_msa_v2_catalog_review.product_review_counts
(
    product_no             bigint                             not null
        primary key,
    review_count           bigint                             not null,
    review_star_rating_sum bigint                             not null,
    version                bigint                             not null,
    created_at             datetime  not null,
    updated_at             datetime  not null
);

create table payment_msa_v2_catalog_review.outbox
(
    outbox_no       bigint                              not null
        primary key,
    shard_key       bigint                              not null,
    event_type      varchar(100)                        not null,
    idempotency_key varchar(128)                        null,
    status          enum ('INIT', 'FAILURE', 'SUCCESS') not null,
    type            varchar(50)                         null,
    partition_key   int                                 null,
    payload         longtext                            null,
    metadata        longtext                            null,
    created_at      datetime   not null,
    updated_at      datetime   not null ,
    constraint idempotency_key
        unique (idempotency_key)
);

create table payment_msa_v2_seller.sellers
(
    seller_no  bigint                               not null
        primary key,
    seller_id  varchar(128)                         not null,
    created_at datetime                             not null,
    updated_at datetime                             not null,
    is_deleted tinyint(1) default 0                 null,
    constraint sellerId
        unique (seller_id)
);

create table payment_msa_v2_seller.outbox
(
    outbox_no       bigint                              not null
        primary key,
    shard_key       bigint                              not null,
    event_type      varchar(100)                        not null,
    idempotency_key varchar(128)                        null,
    status          enum ('INIT', 'FAILURE', 'SUCCESS') not null,
    type            varchar(50)                         null,
    partition_key   int                                 null,
    payload         longtext                            null,
    metadata        longtext                            null,
    created_at      datetime   not null,
    updated_at      datetime   not null,
    constraint idempotency_key
        unique (idempotency_key)
);

create table payment_msa_v2_wallet.wallets
(
    wallet_no  bigint                             not null
        primary key,
    seller_no  bigint                             not null,
    balance    decimal(38, 2)                     null,
    version    int      default 0                 not null,
    created_at datetime  not null,
    updated_at datetime  not null,
    constraint user_id
        unique (seller_no)
);

create table payment_msa_v2_wallet.wallet_transactions
(
    wallet_transaction_no bigint                             not null
        primary key,
    wallet_no             bigint                             not null,
    amount                decimal(38, 2)                     null,
    type                  enum ('CREDIT', 'DEBIT')           not null,
    reference_id          bigint                             not null,
    reference_type        varchar(58)                        not null,
    order_id              varchar(255)                       null,
    idempotency_key       varchar(255)                       null,
    created_at            datetime  null,
    updated_at            datetime  null,
    constraint idempotency_key
        unique (idempotency_key)
);

create table payment_msa_v2_wallet.outbox
(
    outbox_no       bigint                              not null
        primary key,
    shard_key       bigint                              not null,
    event_type      varchar(100)                        not null,
    idempotency_key varchar(128)                        null,
    status          enum ('INIT', 'FAILURE', 'SUCCESS') not null,
    type            varchar(50)                         null,
    partition_key   int                                 null,
    payload         longtext                            null,
    metadata        longtext                            null,
    created_at      datetime not null,
    updated_at      datetime not null,
    constraint idempotency_key
        unique (idempotency_key)
);

INSERT INTO payment_msa_v2_seller.sellers (seller_no, seller_id, created_at, updated_at) VALUES (1, 'A', '2024-11-28 22:55:09', '2024-11-28 22:55:09');
INSERT INTO payment_msa_v2_wallet.wallets (wallet_no, seller_no, balance, version, created_at, updated_at) VALUES (1, 1, 0, 0, '2024-11-28 22:55:09', '2024-11-28 22:55:09');
INSERT INTO payment_msa_v2_catalog.products (product_no, seller_no, product_id, name, price, created_at, updated_at) VALUES (1, 1, 'A', '상품A', 1000.00, '2024-11-28 22:55:09', '2024-11-28 22:55:09');
INSERT INTO payment_msa_v2_catalog.products (product_no, seller_no, product_id, name, price, created_at, updated_at) VALUES (2, 1, 'B', '상품B', 2000.00, '2024-11-28 22:55:09', '2024-11-28 22:55:09');
INSERT INTO payment_msa_v2_catalog.products (product_no, seller_no, product_id, name, price, created_at, updated_at) VALUES (3, 1, 'C', '상품C', 3000.00, '2024-11-28 22:55:09', '2024-11-28 22:55:09');
