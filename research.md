# Araştırma

## Problemimiz
Güncel olarak üç adet client ile çalışıyoruz ve her birine sırasıyla istek gönderiyor, cevap bekliyor ve ancak birinden cevap geldiğinde diğerine istek atıyoruz.  

Bu “sırasıyla işlem yapma durumu” programımızı verimsiz kılıyor. İşlemci / hafıza gibi üniteleri daha verimli kullanmalıyız.  

## Çözüm
Client’lara istek atma mantığımızı bir şekilde paralel işleyebilir hale getirebilirsek verimsizlik ortadan kalkacaktır. Bunun için de concurrency, parallelism ve multithreading kavramlarından faydalanacağız. Bu kavramlara dair araştırma raporum aşağıdadır:  

## Kavramlar ve Açıklamaları

### ● Concurrency
Concurrency, CPU’nun uğraştığı işi hızlıca değiştirerek süreçleri “aynı anda götürüyormuş” izlenimi vermesine denir. Aslında fiziksel olarak aynı anda birden fazla işle uğraşılmaz. Yalnızca CPU “idle / atıl” kalacağı dönemlerde uğraştığı işi değiştirerek sürekli aktif kalır ve bu da birden fazla sürecin sırayla olduğundan daha hızlı halledilmesiyle sonuçlanır.  

**Bizim Senaryomuzdaki Görüntüsü:**  
Client1’e istek attıktan sonra cevap beklenirken Client2 için istek atmaya geçmemiz. Client2 cevabı beklenirken de Client3 için istek atmamız.  

---

### ● Parallelism
Concurrency’den farklı olarak Parallelism’de farklı işlemci / çekirdekler gerçekten de “aynı anda birden fazla iş” hallederler.  

**Bizim Senaryomuzdaki Görüntüsü:**  
- CPU1 Client1’e istek atıp cevabıyla ilgilenirken CPU2 Client2’ye istek atıp cevabıyla ilgilenir.  

---

### ● Multithreading
Aynı program içerisinde birden fazla instruction set (thread) çalıştırmaya yarayan bir programlama konseptidir. Yukarıda bahsedilen concurrency veya parallelismi oluşturmanın yollarından biridir.  

**Bizim Senaryomuzdaki Görüntüsü:**  
- Her client ayrı bir threadde işlem görür.  
