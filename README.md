# 316

В рамках этой задачи мы освоим работу с Rest-template, взаимодействуя с помощью него с открытым Rest API.

Для выполнения задания необходимо:

Создать Maven проект
Включить в файл pom.xml необходимые зависимости.

Описание задачи:

Для работы предоставляется API по URL - http://94.198.50.185:7081/api/users

Ваша задача: Последовательно выполнить следующие операции и получить код для проверки на платформе:

 Получить список всех пользователей
 Когда вы получите ответ на свой первый запрос, вы должны сохранить свой session id, который получен через cookie. Вы получите его в заголовке ответа set-cookie. Поскольку все действия происходят в рамках одной сессии, все дальнейшие запросы должны использовать полученный session id ( необходимо использовать заголовок в последующих запросах )
 Сохранить пользователя с id = 3, name = James, lastName = Brown, age = на ваш выбор. В случае успеха вы получите первую часть кода.
 Изменить пользователя с id = 3. Необходимо поменять name на Thomas, а lastName на Shelby. В случае успеха вы получите еще одну часть кода.
 Удалить пользователя с id = 3. В случае успеха вы получите последнюю часть кода.
В результате выполненных операций вы должны получить итоговый код, сконкатенировав все его части. Количество символов в коде = 18.

Необходимые данные:

      Модель пользователя: 

      public class User {
       private Long id; 
       private String name; 
       private String lastName; 
       private Byte age; 
       ...
      }  

     Список URL для операций и типы запросов:

Получение всех пользователей - …/api/users ( GET )
Добавление пользователя - …/api/users ( POST )
Изменение пользователя - …/api/users ( PUT )
Удаление пользователя - …/api/users /{id} ( DELETE )
 

Требования:

 Все операции должны проводится в рамках одной сессии ( не пытайтесь выполнить операции в ручную, например через POSTMAN, сессия имеет ограниченное время   жизни )
 Все операции должны быть выполнены последовательно по условию
 Для того, чтобы получить необходимый заголовок из запроса, необходимо использовать тип данных ResponseEntity в качестве ответа на ваш запрос.
 
