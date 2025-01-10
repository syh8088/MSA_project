

# 결제 서비스를 이용한 MSA 서비스 만들어보기

서비스는 총 4개 서비스가 존재하고 있습니다.
1. order-service - 주문 서비스 (8080)
2. seller-service - 판매자 서비스 (8083)
3. wallet-service - 지갑 서비스 (8082)
4. catalog-service - 상품 서비스 (8081)
5. hot-catalog-service - 인기 상품 서비스 (8084)
6. catalog-review-service - 리뷰 서비스 (8085)
7. catalog-like-service - 좋아요 상품 서비스 (8086)

자세한 설명은

https://syh8088.github.io/2024/12/14/MSA/paymentMsa/MSA_Payment_Project_1
https://syh8088.github.io/2024/12/15/MSA/paymentMsa/MSA_Payment_Project_2
https://syh8088.github.io/2024/12/16/MSA/paymentMsa/MSA_Payment_Project_3

에서 정리 되어 있습니다. 참고 부탁드립니다.

#### Skill & Tools - Backend
- Java 17
- Spring boot 3.2.0
- Junit5
- JPA
- QueryDsl
- Mysql 8.x
- zookeeper
- Kafka
- kafka-ui
- docker
- Redis


#### Execution
```
./gradlew docker
docker-compose up -d
```

#### Service Endpoint
- 결제 하기
  - http://localhost:8080 접속 및 결제 하기
- Seller 등록 API
  - POST http://localhost:8083/api/sellers -
- Mysql
  - http://localhost:3306
  - root password: 1234
  - database: payment_msa
- Kafka UI
  - http://localhost:8989
- Axon Server Dashboard
  - http://localhost:8024

#### DB initial settings
```mysql
create schema payment_msa;

create table payment_events
(
    no              bigint auto_increment
        primary key,
    member_no       bigint                               null,
    is_payment_done tinyint(1) default 0                 not null,
    is_wallet_done  tinyint(1) default 0                 not null,
    payment_key     varchar(255)                         null,
    order_id        varchar(255)                         null,
    type            enum ('NORMAL')                      not null,
    order_name      varchar(255)                         null,
    method          enum ('CARD')                        null,
    approved_at     datetime                             null,
    created_at      datetime   default CURRENT_TIMESTAMP not null,
    updated_at      datetime   default CURRENT_TIMESTAMP not null,
    constraint order_id
        unique (order_id),
    constraint payment_key
        unique (payment_key)
);

create table payment_orders
(
    no                   bigint auto_increment
        primary key,
    payment_event_no     bigint                                                                                       not null,
    product_no           bigint                                                                                       not null,
    seller_no            bigint                                                                                       null,
    order_id             varchar(255)                                                                                 not null,
    amount               decimal(12, 2)                                                                               not null,
    payment_order_status enum ('NOT_STARTED', 'EXECUTING', 'SUCCESS', 'FAILURE', 'UNKNOWN') default 'NOT_STARTED'     not null,
    failed_count         tinyint                                                            default 0                 not null,
    threshold            tinyint                                                            default 5                 not null,
    created_at           datetime                                                           default CURRENT_TIMESTAMP not null,
    updated_at           datetime                                                           default CURRENT_TIMESTAMP not null
);

create table payment_order_histories
(
    no               bigint auto_increment
        primary key,
    payment_order_no bigint                                                             not null,
    previous_status  enum ('NOT_STARTED', 'EXECUTING', 'SUCCESS', 'FAILURE', 'UNKNOWN') null,
    new_status       enum ('NOT_STARTED', 'EXECUTING', 'SUCCESS', 'FAILURE', 'UNKNOWN') null,
    created_at       datetime default CURRENT_TIMESTAMP                                 not null,
    updated_at       datetime default CURRENT_TIMESTAMP                                 not null,
    changed_by       varchar(255)                                                       null,
    reason           varchar(255)                                                       null
);

create table outbox
(
    no              bigint auto_increment
        primary key,
    idempotency_key varchar(128)                        not null,
    status          enum ('INIT', 'FAILURE', 'SUCCESS') not null,
    type            varchar(50)                         null,
    partition_key   int                                 null,
    payload         longtext                            null,
    metadata        longtext                            null,
    created_at      datetime default CURRENT_TIMESTAMP  not null,
    updated_at      datetime default CURRENT_TIMESTAMP  not null on update CURRENT_TIMESTAMP,
    constraint idempotency_key
        unique (idempotency_key)
);

create table sellers
(
    no         bigint auto_increment
        primary key,
    seller_id  varchar(128) default '0'               not null,
    created_at datetime     default CURRENT_TIMESTAMP not null,
    updated_at datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    is_deleted tinyint(1) default 0                 not null,
    constraint sellerId
        unique (seller_id)
);

create table products
(
    no         bigint auto_increment
        primary key,
    seller_no  bigint                             null,
    product_id varchar(255)                       not null,
    name       varchar(50)                        not null,
    price      decimal(12, 2)                     not null,
    created_at datetime default CURRENT_TIMESTAMP not null,
    updated_at datetime default CURRENT_TIMESTAMP not null
);


create table wallets
(
    no         bigint auto_increment
        primary key,
    seller_no  bigint                                   not null,
    balance    decimal(15, 2) default 0.00              not null,
    version    int            default 0                 not null,
    created_at datetime       default CURRENT_TIMESTAMP not null,
    updated_at datetime       default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint user_id
        unique (seller_no)
);

create table wallet_transactions
(
    no              bigint auto_increment
        primary key,
    wallet_no       bigint                             not null,
    amount          decimal(15, 2)                     not null,
    type            enum ('CREDIT', 'DEBIT')           not null,
    reference_id    bigint                             not null,
    reference_type  varchar(58)                        not null,
    order_id        varchar(255)                       null,
    idempotency_key varchar(255)                       null,
    created_at      datetime default CURRENT_TIMESTAMP null,
    updated_at      datetime default CURRENT_TIMESTAMP null,
    constraint idempotency_key
        unique (idempotency_key)
);
INSERT INTO sellers (no, seller_id, created_at, updated_at) VALUES (1, 'A', '2024-11-28 22:55:09', '2024-11-28 22:55:09');
INSERT INTO wallets (no, seller_no, balance, version, created_at, updated_at) VALUES (1, 1, 0, 0, '2024-11-28 22:55:09', '2024-11-28 22:55:09');

INSERT INTO products (no, seller_no, product_id, name, price, created_at, updated_at) VALUES (1, 1, 'A', '상품A', 1000.00, '2024-11-28 22:55:09', '2024-11-28 22:55:09');
INSERT INTO products (no, seller_no, product_id, name, price, created_at, updated_at) VALUES (2, 1, 'B', '상품B', 2000.00, '2024-11-28 22:55:09', '2024-11-28 22:55:09');
INSERT INTO products (no, seller_no, product_id, name, price, created_at, updated_at) VALUES (3, 1, 'C', '상품C', 3000.00, '2024-11-28 22:55:09', '2024-11-28 22:55:09');
```



