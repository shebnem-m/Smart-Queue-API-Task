# Smart-Queue-API-Task

Səhiyyə müəssisələrində xəstə növbələrinin idarə olunması üçün hazırlanmış Spring Boot RESTful API layihəsi. Tətbiq xəstə qeydiyyatını, real vaxt rejimində növbə statuslarının keçidini və eyni anda gələn sorğuların pessimistic locking (pessimistik kilidləmə) mexanizmi ilə təhlükəsiz idarə olunmasını təmin edir.

---

## Xüsusiyyətlər

- **Xəstə Qeydiyyatı:** Yeni xəstələrin sistemə əlavə olunması və avtomatik `WAITING` statusunun mənimsədilməsi.
- **Konkurentlik (Concurrency) Nəzarəti:** Yüksək sorğu axını zamanı eyni xəstənin təkrar çağırılmasının qarşısını almaq üçün JPA `PESSIMISTIC_WRITE` kilidlənməsindən istifadə.
- **Növbə Naviqasiyası:** Növbəti xəstənin çağırılması və statusunun `WAITING` dən `SERVING` ə dəyişdirilməsi.
- **RESTful Endpoints:** Düzgün HTTP status kodları və JSON formatında cavab qaytaran təmiz API memarlığı.

---

## Texnologiya Steki

- **Java:** 17+
- **Freymvork:** Spring Boot 3
- **ORM / Verilənlər Bazası:** Spring Data JPA, Hibernate, MySQL 8.0
- **Validasiya:** Jakarta Bean Validation
- **Test və Alətlər:** Postman, Maven

---

## API Endpoint-ləri

| Metod | Endpoint | Təsvir | Sorğu Body Nümunəsi |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/queue` | Növbəyə yeni xəstə əlavə etmək | `{"name": "Samira Muradova", "phoneNumber": "+994703234567", "dateOfBirth": "1981-01-22"}` |
| `POST` | `/api/queue/next` | Növbədəki növbəti xəstəni çağırmaq | *Boş (None)* |
| `DELETE` | `/api/queue/{id}` | İD-yə görə xəstə qeydini silmək | *Boş (None)* |

---

## Quraşdırma və Konfiqurasiya

Layihəni lokal mühitdə işə salmaq üçün `src/main/resources/application.properties` faylında MySQL məlumatlarınızı qeyd edin:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/BAZA_ADI?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=ISTIFADECI_ADI
spring.datasource.password=PAROL
spring.jpa.hibernate.ddl-auto=update
