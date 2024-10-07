CREATE TABLE Brand
(
    id   INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
CREATE TABLE Price
(
    price_list      BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id        INT,
    start_date      TIMESTAMP WITH TIME ZONE,
    end_date        TIMESTAMP WITH TIME ZONE,
    product_id      INT,
    priority        INT,
    price           DECIMAL(10, 2),
    currency        VARCHAR(3),
    last_update    TIMESTAMP WITH TIME ZONE,
    last_update_by VARCHAR(255),
    CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES Brand (id)
);
