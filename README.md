Żeby część projektu w pakiecie 'transmission' się kompilowała i dawała uruchomić należy w Eclipse dodać bibliotekę z katalogu includes:

właściwości projektu -> Java Build Path -> Libraries -> add Jar

Jak zobaczyć jak działa transmisja? Najprościej: dajemy "run" na klasie TCPServer, a potem TCPCLientFactory i patrzymy w kod ich metod main.