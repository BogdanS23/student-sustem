# Система для управления оценками студентов

### 1. Клонирование

```bash
git clone https://github.com/BogdanS23/student-sustem.git
cd student-sustem
```

### 2. Запуск

Соберите и запустите:

```bash
docker-compose up --build
```

Проверьте статус:

```bash
docker ps
```

### 3. Запросы к API

API доступно на порту `8080`. Запросы приведены в формате Curl.

#### Добавить оценку/студента
  При создании оценки происходит поиск указанного студента, если он отсутствует - создается новый студент.

  ```bash
  curl -X POST http://localhost:8080/api/grades -H "Content-Type: application/json" \
  -d '{
    "studentFirstName": "Ivan",
    "studentLastName": "Ivanov",
    "groupName": "Group A",
    "subject": "Mathematics",
    "value": 4,
    "date": "2025-05-14"
  }' 
  ```
- **Ожидаемый ответ**:
  ```
  Grade sent to Kafka
  ```


### Поиск
 #### Поиск оценок студентов
  ```bash
  curl http://localhost:8080/api/grades/search
  ```

- **Ожидаемый ответ**:
  ```json
  [
    {
        "studentFirstName": "Ivan",
        "studentLastName": "Sidorov",
        "groupName": "Group A",
        "subject": "Mathematics",
        "value": 2.0,
        "date": "2025-05-12"
    },
    {
        "studentFirstName": "Ivan",
        "studentLastName": "Sidorov",
        "groupName": "Group A",
        "subject": "Informatics",
        "value": 4.0,
        "date": "2025-05-11"
    },
  ]
  ```

#### Так же можно указать query для точного поиска, например по предмету, имени, или оценке
  ```bash
  curl http://localhost:8080/api/grades/search?query=Mathematics
  ```

- **Ожидаемый ответ**:
  ```json
  [
    {
        "studentFirstName": "Ivan",
        "studentLastName": "Sidorov",
        "groupName": "Group A",
        "subject": "Mathematics",
        "value": 2.0,
        "date": "2025-05-12"
    },
  ]
  ```
#### Аналогично для студентов 
```bash
  curl http://localhost:8080/api/students/search?query=Ivan
  ```
**Ожидаемый ответ**:
```json
[
    {
        "firstName": "Ivan",
        "lastName": "Sidorov",
        "groupName": "Group A",
        "grades": [
            {
            "studentFirstName": "Ivan",
            "studentLastName": "Sidorov",
            "groupName": "Group A",
            "subject": "Mathematics",
            "value": 2.0,
            "date": "2025-05-12"
            },
            {
            "studentFirstName": "Ivan",
            "studentLastName": "Sidorov",
            "groupName": "Group A",
            "subject": "Informatics",
            "value": 4.0,
            "date": "2025-05-11"
            },
        ]
    }
]
```


#### Отчет: Студенты, претендующие на красный диплом

  ```bash
  curl http://localhost:8080/api/reports/honor-students
  ```
  
- **Ожидаемый ответ**:
  ```json
  [
    {
        "firstName": "Ivan",
        "lastName": "Smirnov",
        "groupName": "Group A",
        "grades": [
            {
                "studentFirstName": "Ivan",
                "studentLastName": "Smirnov",
                "groupName": "Group A",
                "subject": "Mathematics",
                "value": 5.0,
                "date": "2025-05-10"
            },
            {
                "studentFirstName": "Ivan",
                "studentLastName": "Smirnov",
                "groupName": "Group A",
                "subject": "Informatics",
                "value": 5.0,
                "date": "2025-05-08"
            }
        ]
    }
  ]
  ```

#### Отчет: Студенты по предмету  


  ```bash
  curl http://localhost:8080/api/reports/students-by-subject?subject=Informatics
  ```

- **Ожидаемый ответ**:
  ```json
  [
    {
        "firstName": "Ivan",
        "lastName": "Sidorov",
        "groupName": "Group A",
        "grades": [
            {
                "studentFirstName": "Ivan",
                "studentLastName": "Sidorov",
                "groupName": "Group A",
                "subject": "Informatics",
                "value": 4.0,
                "date": "2025-05-17"
            },
            {
                "studentFirstName": "Ivan",
                "studentLastName": "Sidorov",
                "groupName": "Group A",
                "subject": "Informatics",
                "value": 5.0,
                "date": "2025-05-17"
            }
        ]
    },
    {
        "firstName": "Alexey",
        "lastName": "Smirnov",
        "groupName": "Group B",
        "grades": [
            {
                "studentFirstName": "Alexey",
                "studentLastName": "Smirnov",
                "groupName": "Group B",
                "subject": "Informatics",
                "value": 5.0,
                "date": "2025-05-17"
            }
        ]
    }
  ]
  ```

#### Отчет: Студенты по группе

  ```bash
  curl http://localhost:8080/api/reports/students-by-group?groupName=Group B
  ```
- **Ожидаемый ответ**:
  ```json
  [
    {
        "firstName": "Alexey",
        "lastName": "Smirnov",
        "groupName": "Group B",
        "grades": [
            {
                "studentFirstName": "Alexey",
                "studentLastName": "Smirnov",
                "groupName": "Group B",
                "subject": "Informatics",
                "value": 5.0,
                "date": "2025-05-17"
            }
        ]
    }
  ]
  ```

### 4. Остановка

```bash
docker-compose down
```

