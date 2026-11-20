package com.example.project1
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.io.File

class City(id: Long, name: String, country: String, population: Int) {

}

class Class {


    fun openReadyDatabase(context: Context): SQLiteDatabase {
        // 1. Получаем путь к файлу в приватной папке приложения
        val dbFile: File = context.getDatabasePath("filebd.db")

        // 2. Проверяем, что файл существует (если копировали из assets)
        require(dbFile.exists()) { "Файл базы не найден:${dbFile.absolutePath}" }

        // 3. Открываем базу в режиме ТОЛЬКО ДЛЯ ЧТЕНИЯ (безопасно для справочников)
        return SQLiteDatabase.openDatabase(
            dbFile.path,                  // Абсолютный путь
            null,                         // Фабрика курсоров (не нужна)
            SQLiteDatabase.OPEN_READONLY  // Флаг доступа (или)
        )
    }


    fun insertCity(db: SQLiteDatabase, name: String, country: String,
                   population: Int): Long {
        // 1. Создаем контейнер для данных и кладем в него значения
        val values = ContentValues().apply {
            put("name", name)             // Колонка "name" = переменная name
            put("country", country)       // Колонка "country" = переменная
            country
            put("population", population) // Колонка "population" = переменная
            population
        }

        // 2. Вызываем insert и получаем ID новой строки (или -1 при ошибке)
        val newRowId = db.insert(
            "cities", // Имя таблицы
            null,     // nullColumnHack (нужен, если values пустой)
            values    // Сами данные
        )
        return newRowId
    }


    fun findCitiesByCountry(db: SQLiteDatabase, country: String):
                List<City> {
            val result = mutableListOf<City>()

            // 1. Выполняем сырой запрос. Знак "?" защищает от инъекций. 
            val cursor = db.rawQuery(
                "SELECT id, name, country, population FROM cities WHERE country = ?",
                arrayOf(country) // Значения подставляются вместо "?"
            )

            // 2. use {} гарантирует, что курсор закроется после чтения (даже при ошибке)
            cursor.use { c ->
                // 3. Кэшируем индексы колонок ДО цикла (так быстрее) 
                val idIdx = c.getColumnIndexOrThrow("id")
                val nameIdx = c.getColumnIndexOrThrow("name")
                val countryIdx = c.getColumnIndexOrThrow("country")
                val popIdx = c.getColumnIndexOrThrow("population")

                // 4. moveToNext() возвращает false, когда строки закончились 
                while (c.moveToNext()) {
                    result.add(
                        City(
                            id = c.getLong(idIdx),
                            name = c.getString(nameIdx),
                            country = c.getString(countryIdx),
                            population = c.getInt(popIdx)
                        )
                    )
                }
            }
            return result
        }

        fun deleteCityByName(db: SQLiteDatabase, name: String): Int {
            // Указываем условие "name = ?" (без слова WHERE) и аргументы
            val deletedRows = db.delete(
                "cities",   // Таблица
                "name = ?", // Условие
                arrayOf(name) // Аргумент
            )
            return deletedRows // Вернет количество удаленных строк
        }

        fun updatePopulation(
            db: SQLiteDatabase, cityName: String,
            newPop: Int
        ): Int {
            // 1. В ContentValues кладем ТОЛЬКО то поле, которое хотим изменить
            val values = ContentValues().apply {
                put("population", newPop)
            }
            // 2. Обновляем строку, где имя совпадает с cityName
            val updatedRows = db.update(
                "cities",       // Таблица
                values,         // Новые значения
                "name = ?",     // Условие
                arrayOf(cityName) // Аргумент
            )
            return updatedRows // Вернет количество обновленных строк
        }

    }


class User(id: Int, name: String) {

}
