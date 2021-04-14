[![Build Status](https://www.travis-ci.com/RomanRusanov/job4j_url_shortcut.svg?branch=main)](https://www.travis-ci.com/RomanRusanov/job4j_url_shortcut)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/08183c487b3541b7847a8e61cf342b7b)](https://www.codacy.com/gh/RomanRusanov/job4j_url_shortcut/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=RomanRusanov/job4j_url_shortcut&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/RomanRusanov/job4j_url_shortcut/branch/main/graph/badge.svg)](https://codecov.io/gh/RomanRusanov/job4j_url_shortcut)

#Сервис - UrlShortCut
Чтобы обеспечить безопасность пользователей, все ссылки на сайте заменяются ссылками на наш сервис.

Сервис работает через REST API.

Требуемый функционал.

###1. Регистрация сайта.

Сервисом могут пользоваться разные сайты. Каждому сайту выдается пару пароль и логин.

Чтобы зарегистрировать сайт в систему нужно отправить запроса.

URL

POST /registration
C телом JSON объекта.

{site : "job4j.ru"}

Ответ от сервера.

{registration : true/false, login: УНИКАЛЬНЫЙ_КОД, password : УНИКАЛЬНЫЙ_КОД}

Флаг registration указывает, что регистрация выполнена или нет, то есть сайт уже есть в системе.

###2. Авторизация.

Во всех остальных вызовах сервис должен проверять авторизацию пользователя.

Авторизацию сделать через JWT. Пользователь отправляет POST запрос с login и password и получает ключ.

Этот ключ отправляет в запросе в блоке HEAD.

Authorization: Bearer e25d31c5-db66-4cf2-85d4-8faa8c544ad6


###3. Регистрация URL.

Поле того, как пользователь зарегистрировал свой сайт он может отправлять на сайт ссылки и получать преобразованные ссылки.

Пример.

Отправляем URL.

https://job4j.ru/TrackStudio/task/8993?thisframe=true

Получаем.

Ключ ZRUfD2 ассоциирован с URL.

Опишем вызовы.

POST /convert
C телом JSON объекта.

{url: "https://job4j.ru/TrackStudio/task/8993?thisframe=true"}

Ответ от сервера.

{code: УНИКАЛЬНЫЙ_КОД}

###4. Переадресация. Выполняется без авторизации.

Когда сайт отправляет ссылку с кодом в ответ нужно вернуть ассоциированный адрес и статус 302.

Опишем вызовы.

GET /redirect/УНИКАЛЬНЫЙ_КОД

Ответ от сервера в заголовке.

HTTP CODE - 302 REDIRECT URL

###5. Статистика.

В сервисе считается количество вызовов каждого адреса.

По сайту можно получить статистку всех адресов и количество вызовов этого адреса.

Опишем вызовы.

GET /statistic
Ответ от сервера JSON.

    {
    
        {url : URL, total : 0},
    
        {url : "https://job4j.ru/TrackStudio/task/23", total : 103}
    }