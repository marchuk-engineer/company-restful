**SWAGGER API: https://app.swaggerhub.com/apis-docs/MISHAAKAMICHAEL999/smida-api-restful/1.0**

**Test coverage:**

![image](https://github.com/marchuk-engineer/smida-techtask/assets/112648286/dfc979c3-25ac-404f-853b-c2a2b94e819d)


***Проект використовує environment змінні! ДОДАЙТЕ ВАШІ ЗНАЧЕННЯ В ФАЙЛ .env Файл розташований у папці проекту***

Ендпоінти для реєстрації, входу та виходу дозволені для всіх, інші потребують аутентифікації

Реєстрація, Вхід та Вихід: Дозволені для всіх користувачів.
Інші ендпоінти вимагають аутентифікації.

*Read: User, Editor, Admin
Write: Editor, Admin
Delete: Admin*

**Використовуйте наступних вже зареєстрованих користувачів:**

***АДМІНІСТРАТОР: ім'я користувача: smida-admin, пароль: admin***

***РЕДАКТОР: ім'я користувача: smida-editor, пароль: editor***

***КОРИСТУВАЧ: ім'я користувача: smida-user, пароль: user***

<details lang="java">
<summary>Як почати:</summary>

<details lang="java">
<summary>В Intellij Idea:</summary>




1. Клонуйте проект.
2. Запустіть тест
```
mvn test
```
3. Запустіть сервер
```
mvn clean install -Dmaven.test.skip=true
```

</details>

<details lang="java">
<summary>Docker:</summary>

Клонуйте проект та запустіть compose.yaml
```
docker.exe compose -f compose.yaml  up 
```

</details>

<details lang="java">
<summary>Опис завдання:</summary>

1. Створення структури бази даних:
	Використовувати PostgreSQL для зберігання основної інформації про компанії та їх звіти.
	Використовувати MongoDB для зберігання детальної інформації про звіти (наприклад, детальні фінансові показники).

2. Сутності:
	Компанія (Company):
		id (UUID)
		name (String)
		registration_number (String)
		address (String)
		created_at (Timestamp)
	Звіт (Report):
		id (UUID)
		company_id (UUID, зовнішній ключ на компанію)
		report_date (Timestamp)
		total_revenue (Decimal)
		net_profit (Decimal)
	Деталі звіту (ReportDetails) у MongoDB:
		report_id (UUID, зовнішній ключ на звіт)
		financial_data (JSON)
		comments (String)
	
3. Вимоги до функціональності:
	Реалізувати CRUD операції для компаній та їх звітів.
	Реалізувати ендпоінт для отримання всіх звітів конкретної компанії.
	Реалізувати ендпоінт для отримання детальної інформації про звіт.
	Забезпечити міграції бази даних за допомогою Liquibase.
	Запакувати сервіс у Docker-контейнер для легкого розгортання.
	Реалізувати аутентифікацію та авторизацію за допомогою Spring Security.

4. Технології:
	Java 8 -11
	Spring 
	Spring Data JPA
	Hibernate
	Spring Data MongoDB
	Liquibase
	Spring Security
	Docker
	PostgreSQL
	MongoDB

5. Технічні деталі:
	Проект повинен бути виконаний у вигляді Maven проекту.
	Написати базові unit та integration тести для основних операцій.
	Використовувати Spring MVC для створення RESTful веб-сервісів.
	Конфігурації бази даних повинні бути винесені в application.properties або application.yml.
	Забезпечити Dockerfile для створення Docker-образу.
	Забезпечити docker-compose.yml для розгортання сервісу з усіма залежностями.
	Код повинен бути розміщений у публічному репозиторії на GitHub або GitLab з інструкцією по запуску.

6. Критерії оцінки:
	Коректність реалізації CRUD операцій.
	Використання відповідних аннотацій та патернів Spring MVC.
	Якість та структура коду.
	Покриття коду тестами.
	Коректна робота міграцій Liquibase.
	Правильність конфігурації Dockerfile та docker-compose.yml.
	Реалізація аутентифікації та авторизації за допомогою Spring Security.
	Документація та коментарі у коді.

**Термин виконання 5 днів.**

</details>




