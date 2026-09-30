FROM php:8.2-fpm-alpine

# Zarur extensions
RUN apk add --no-cache \
        bash \
        libzip-dev \
        zlib-dev \
        libpng-dev \
        libjpeg-turbo-dev \
        freetype-dev \
        icu-dev \
        oniguruma-dev \
        libxml2-dev \
        c-client-dev \
        gmp-dev \
        openldap-dev \
        openssl-dev \
        pcre-dev \
        bzip2-dev \
        enchant-dev \
        pdo_dblib-dev \
        unixodbc-dev \
        pspell-dev \
        pdflib-dev \
        snmp-dev \
        librabbitmq-dev \
        curl-dev

# PHP extensions o‘rnatish
RUN docker-php-ext-install \
        bcmath \
        bz2 \
        calendar \
        ctype \
        curl \
        dba \
        dom \
        exif \
        fileinfo \
        filter \
        ftp \
        gd \
        gettext \
        gmp \
        iconv \
        imap \
        intl \
        mbstring \
        mysqli \
        mysqlnd \
        odbc \
        openssl \
        pcntl \
        pdo \
        pdo_mysql \
        pdo_pgsql \
        pdo_sqlite \
        pgsql \
        posix \
        shmop \
        soap \
        sockets \
        xml \
        xmlreader \
        xmlwriter \
        zip \
        opcache

# PECL orqali qo‘shimcha
RUN pecl install apcu imagick memcache memcached redis oauth mailparse \
    && docker-php-ext-enable apcu imagick memcache memcached redis oauth mailparse

WORKDIR /var/www
COPY ./www /var/www

CMD ["php-fpm"]
