Тестирование веб-приложение Stellar Burgers
Автотесты для запуска вбраузерах:

Google Chrome (mvn clean test)
-Яндекс.Браузере (mvn clean test -DbrowserType=Yandex)
test\java\stellarburgers\ConstStellarburgers- настройки путей для запуска в Яндекс.Браузере.
AccountProfileTest:
-Переход из личного кабинета в конструктор по клику на «Конструктор»
-Переход из личного кабинета в конструктор по логотипу Stellar Burgers.
-Выход по кнопке «Выйти» в личном кабинете.
AuthorizationTest:
-Вход по кнопке «Войти в аккаунт» на главной,
-Вход через кнопку «Личный кабинет»,
-Вход через кнопку в форме регистрации,
-Вход через кнопку в форме восстановления пароля.
HomePageButtonTest:
-Переход по клику на «Личный кабинет» без авторизации.
-Переход по клику на «Личный кабинет» c авторизацией.
-Переход по клику на «Войти в аккаунт» без авторизации.
HomePageTest:
-Переход по разделам «Конструктор»
RegisterTest
-Регистрация
-Успешная регистрация
-Ошибка для некорректного пароля. Минимальный пароль — шесть символов.
Подключены библиотеки:Allure, Junit, RestAssured, Selenium
Allure-отчёт.
 
 
