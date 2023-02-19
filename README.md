# WB-telegrambot
Telegram bot for work with Wildberries

## To start it
Fill in application.properties or create environment variables:
 - bot.username = ${BOT_USERNAME}
 - bot.token = ${BOT_TOKEN}
 - spring.datasource.url = jdbc:mysql://${DATA_BASE_URL}
 - spring.datasource.username = ${DATA_BASE_USERNAME}
 - spring.datasource.password = ${DATA_BASE_PASSWORD}

For encryption in dataBase (any int value)
 - encrypt.password = ${ENCRYPT_PASSWORD}

For tests WB client
 - wb.standart.test.token = ${WB_STD_TEST_TOKEN}
 
