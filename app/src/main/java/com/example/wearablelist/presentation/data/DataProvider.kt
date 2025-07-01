package com.example.wearablelist.presentation.data

import com.example.wearablelist.presentation.ItemType
import com.example.wearablelist.presentation.ListItem

object DataProvider {

    fun getTaskList(): List<ListItem> = listOf(
        ListItem(1, "Mis Tareas", type = ItemType.HEADER),
        ListItem(2, "Comprar leche", "Supermercado - 18:00"),
        ListItem(3, "Llamar a mamá", "Pendiente desde ayer"),
        ListItem(4, "Ejercicio", "30 min - Rutina de cardio"),
        ListItem(5, "Reunión trabajo", "15:30 - Sala de juntas"),
        ListItem(6, "Configuración", type = ItemType.ACTION),
        ListItem(7, "Leer libro", "Capítulo 5 - Novela"),
        ListItem(8, "Preparar cena", "Pasta con verduras"),
        ListItem(9, "Revisar emails", "Bandeja de entrada"),
        ListItem(10, "Acerca de", type = ItemType.ACTION)
    )

    fun getContactsList(): List<ListItem> = listOf(
        ListItem(1, "Contactos", type = ItemType.HEADER),
        ListItem(2, "Ana García", "Trabajo - Gerente"),
        ListItem(3, "Carlos López", "Amigo - Universidad"),
        ListItem(4, "María Rodríguez", "Familia - Hermana"),
        ListItem(5, "Juan Pérez", "Vecino - Edificio"),
        ListItem(6, "Llamar a todos", type = ItemType.ACTION),
        ListItem(7, "Sofia Martín", "Trabajo - Diseñadora"),
        ListItem(8, "Diego Santos", "Gym - Entrenador"),
        ListItem(9, "Laura Jiménez", "Escuela - Profesora"),
        ListItem(10, "Agregar contacto", type = ItemType.ACTION)
    )

    fun getSettingsList(): List<ListItem> = listOf(
        ListItem(1, "Configuración", type = ItemType.HEADER),
        ListItem(2, "Pantalla", "Brillo y tema"),
        ListItem(3, "Sonido", "Volumen y vibración"),
        ListItem(4, "Conectividad", "WiFi y Bluetooth"),
        ListItem(5, "Aplicaciones", "Gestionar apps"),
        ListItem(6, "Restablecer", type = ItemType.ACTION),
        ListItem(7, "Batería", "Uso y optimización"),
        ListItem(8, "Privacidad", "Permisos y datos"),
        ListItem(9, "Actualización", "Sistema y apps"),
        ListItem(10, "Ayuda", type = ItemType.ACTION)
    )

    fun getFitnessList(): List<ListItem> = listOf(
        ListItem(1, "Actividad Física", type = ItemType.HEADER),
        ListItem(2, "Pasos hoy", "8,245 / 10,000 pasos"),
        ListItem(3, "Calorías", "320 kcal quemadas"),
        ListItem(4, "Distancia", "6.2 km recorridos"),
        ListItem(5, "Iniciar entrenamiento", type = ItemType.ACTION),
        ListItem(6, "Frecuencia cardíaca", "72 bpm - Normal"),
        ListItem(7, "Sueño", "7h 23min anoche"),
        ListItem(8, "Agua", "1.2L / 2L objetivo"),
        ListItem(9, "Actividad semanal", "4/5 días activos"),
        ListItem(10, "Ver estadísticas", type = ItemType.ACTION)
    )

    fun getNotificationsList(): List<ListItem> = listOf(
        ListItem(1, "Notificaciones", type = ItemType.HEADER),
        ListItem(2, "WhatsApp", "3 mensajes nuevos"),
        ListItem(3, "Email", "2 correos sin leer"),
        ListItem(4, "Calendario", "Reunión en 30 min"),
        ListItem(5, "Marcar como leído", type = ItemType.ACTION),
        ListItem(6, "Instagram", "5 me gusta nuevos"),
        ListItem(7, "Banco", "Movimiento en cuenta"),
        ListItem(8, "Clima", "Lluvia esta tarde"),
        ListItem(9, "Sistema", "Actualización disponible"),
        ListItem(10, "Limpiar todo", type = ItemType.ACTION)
    )
}