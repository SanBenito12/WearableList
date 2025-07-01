package com.example.wearablelist.presentation.data

import com.example.wearablelist.presentation.ItemType
import com.example.wearablelist.presentation.ListItem

object SportsDataProvider {

    fun getChampionsLeagueTeams(): List<ListItem> = listOf(
        ListItem(1, "⚽ Champions League", type = ItemType.HEADER),
        ListItem(2, "Real Madrid", "🇪🇸 ESP - 14 títulos", route = "detail/real_madrid/Real Madrid"),
        ListItem(3, "Barcelona", "🇪🇸 ESP - 5 títulos", route = "detail/barcelona/Barcelona"),
        ListItem(4, "Manchester City", "🏴󠁧󠁢󠁥󠁮󠁧󠁿 ENG - 1 título", route = "detail/man_city/Manchester City"),
        ListItem(5, "Bayern Munich", "🇩🇪 GER - 6 títulos", route = "detail/bayern/Bayern Munich"),
        ListItem(6, "Liverpool", "🏴󠁧󠁢󠁥󠁮󠁧󠁿 ENG - 6 títulos", route = "detail/liverpool/Liverpool"),
        ListItem(7, "PSG", "🇫🇷 FRA - 0 títulos", route = "detail/psg/PSG"),
        ListItem(8, "AC Milan", "🇮🇹 ITA - 7 títulos", route = "detail/milan/AC Milan"),
        ListItem(9, "Inter Milan", "🇮🇹 ITA - 3 títulos", route = "detail/inter/Inter Milan"),
        ListItem(10, "Juventus", "🇮🇹 ITA - 2 títulos", route = "detail/juventus/Juventus"),
        ListItem(11, "Atlético Madrid", "🇪🇸 ESP - 0 títulos", route = "detail/atletico/Atlético Madrid"),
        ListItem(12, "Chelsea", "🏴󠁧󠁢󠁥󠁮󠁧󠁿 ENG - 2 títulos", route = "detail/chelsea/Chelsea"),
        ListItem(13, "Arsenal", "🏴󠁧󠁢󠁥󠁮󠁧󠁿 ENG - 0 títulos", route = "detail/arsenal/Arsenal"),
        ListItem(14, "Ver más equipos", type = ItemType.ACTION, route = "teams_extended")
    )

    fun getNBATeams(): List<ListItem> = listOf(
        ListItem(1, "🏀 NBA Teams", type = ItemType.HEADER),
        ListItem(2, "Los Angeles Lakers", "🏆 17 campeonatos", route = "detail/lakers/Los Angeles Lakers"),
        ListItem(3, "Boston Celtics", "🏆 17 campeonatos", route = "detail/celtics/Boston Celtics"),
        ListItem(4, "Golden State Warriors", "🏆 7 campeonatos", route = "detail/warriors/Golden State Warriors"),
        ListItem(5, "Chicago Bulls", "🏆 6 campeonatos", route = "detail/bulls/Chicago Bulls"),
        ListItem(6, "San Antonio Spurs", "🏆 5 campeonatos", route = "detail/spurs/San Antonio Spurs"),
        ListItem(7, "Miami Heat", "🏆 3 campeonatos", route = "detail/heat/Miami Heat"),
        ListItem(8, "Philadelphia 76ers", "🏆 3 campeonatos", route = "detail/76ers/Philadelphia 76ers"),
        ListItem(9, "Detroit Pistons", "🏆 3 campeonatos", route = "detail/pistons/Detroit Pistons"),
        ListItem(10, "Milwaukee Bucks", "🏆 2 campeonatos", route = "detail/bucks/Milwaukee Bucks"),
        ListItem(11, "New York Knicks", "🏆 2 campeonatos", route = "detail/knicks/New York Knicks"),
        ListItem(12, "Houston Rockets", "🏆 2 campeonatos", route = "detail/rockets/Houston Rockets"),
        ListItem(13, "Toronto Raptors", "🏆 1 campeonato", route = "detail/raptors/Toronto Raptors"),
        ListItem(14, "Ver estadísticas", type = ItemType.ACTION, route = "nba_stats")
    )

    fun getMLBTeams(): List<ListItem> = listOf(
        ListItem(1, "⚾ MLB Teams", type = ItemType.HEADER),
        ListItem(2, "New York Yankees", "🏆 27 títulos", route = "detail/yankees/New York Yankees"),
        ListItem(3, "St. Louis Cardinals", "🏆 11 títulos", route = "detail/cardinals/St. Louis Cardinals"),
        ListItem(4, "Oakland Athletics", "🏆 9 títulos", route = "detail/athletics/Oakland Athletics"),
        ListItem(5, "Boston Red Sox", "🏆 9 títulos", route = "detail/red_sox/Boston Red Sox"),
        ListItem(6, "San Francisco Giants", "🏆 8 títulos", route = "detail/giants/San Francisco Giants"),
        ListItem(7, "Detroit Tigers", "🏆 4 títulos", route = "detail/tigers/Detroit Tigers"),
        ListItem(8, "Los Angeles Dodgers", "🏆 7 títulos", route = "detail/dodgers/Los Angeles Dodgers"),
        ListItem(9, "Pittsburgh Pirates", "🏆 5 títulos", route = "detail/pirates/Pittsburgh Pirates"),
        ListItem(10, "Cincinnati Reds", "🏆 5 títulos", route = "detail/reds/Cincinnati Reds"),
        ListItem(11, "Baltimore Orioles", "🏆 3 títulos", route = "detail/orioles/Baltimore Orioles"),
        ListItem(12, "Minnesota Twins", "🏆 3 títulos", route = "detail/twins/Minnesota Twins"),
        ListItem(13, "Toronto Blue Jays", "🏆 2 títulos", route = "detail/blue_jays/Toronto Blue Jays"),
        ListItem(14, "Ver calendario", type = ItemType.ACTION, route = "mlb_schedule")
    )

    fun getLaLigaTeams(): List<ListItem> = listOf(
        ListItem(1, "🇪🇸 La Liga", type = ItemType.HEADER),
        ListItem(2, "Real Madrid", "⭐ 35 títulos", route = "detail/real_madrid_liga/Real Madrid"),
        ListItem(3, "Barcelona", "⭐ 27 títulos", route = "detail/barcelona_liga/Barcelona"),
        ListItem(4, "Atlético Madrid", "⭐ 11 títulos", route = "detail/atletico_liga/Atlético Madrid"),
        ListItem(5, "Athletic Bilbao", "⭐ 8 títulos", route = "detail/athletic/Athletic Bilbao"),
        ListItem(6, "Valencia", "⭐ 6 títulos", route = "detail/valencia/Valencia"),
        ListItem(7, "Real Sociedad", "⭐ 2 títulos", route = "detail/real_sociedad/Real Sociedad"),
        ListItem(8, "Sevilla", "⭐ 1 título", route = "detail/sevilla/Sevilla"),
        ListItem(9, "Deportivo La Coruña", "⭐ 1 título", route = "detail/deportivo/Deportivo"),
        ListItem(10, "Real Betis", "⭐ 1 título", route = "detail/betis/Real Betis"),
        ListItem(11, "Villarreal", "🟡 Europa League", route = "detail/villarreal/Villarreal"),
        ListItem(12, "Real Mallorca", "🏝️ Islas Baleares", route = "detail/mallorca/Real Mallorca"),
        ListItem(13, "Tabla de posiciones", type = ItemType.ACTION, route = "laliga_table")
    )

    fun getPremierLeagueTeams(): List<ListItem> = listOf(
        ListItem(1, "🏴󠁧󠁢󠁥󠁮󠁧󠁿 Premier League", type = ItemType.HEADER),
        ListItem(2, "Manchester United", "🔴 20 títulos", route = "detail/man_utd/Manchester United"),
        ListItem(3, "Liverpool", "🔴 19 títulos", route = "detail/liverpool_pl/Liverpool"),
        ListItem(4, "Arsenal", "🔴 13 títulos", route = "detail/arsenal_pl/Arsenal"),
        ListItem(5, "Everton", "🔵 9 títulos", route = "detail/everton/Everton"),
        ListItem(6, "Aston Villa", "🟣 7 títulos", route = "detail/villa/Aston Villa"),
        ListItem(7, "Sunderland", "🔴 6 títulos", route = "detail/sunderland/Sunderland"),
        ListItem(8, "Newcastle United", "⚫ 4 títulos", route = "detail/newcastle/Newcastle"),
        ListItem(9, "Sheffield Wednesday", "🔵 4 títulos", route = "detail/sheffield_wed/Sheffield Wed"),
        ListItem(10, "Chelsea", "🔵 6 títulos", route = "detail/chelsea_pl/Chelsea"),
        ListItem(11, "Manchester City", "🔵 9 títulos", route = "detail/man_city_pl/Manchester City"),
        ListItem(12, "Tottenham", "⚪ 2 títulos", route = "detail/tottenham/Tottenham"),
        ListItem(13, "Ver partidos hoy", type = ItemType.ACTION, route = "pl_matches")
    )

    fun getNFLTeams(): List<ListItem> = listOf(
        ListItem(1, "🏈 NFL Teams", type = ItemType.HEADER),
        ListItem(2, "New England Patriots", "🏆 6 Super Bowls", route = "detail/patriots/New England Patriots"),
        ListItem(3, "Pittsburgh Steelers", "🏆 6 Super Bowls", route = "detail/steelers/Pittsburgh Steelers"),
        ListItem(4, "Dallas Cowboys", "🏆 5 Super Bowls", route = "detail/cowboys/Dallas Cowboys"),
        ListItem(5, "San Francisco 49ers", "🏆 5 Super Bowls", route = "detail/49ers/San Francisco 49ers"),
        ListItem(6, "Green Bay Packers", "🏆 4 Super Bowls", route = "detail/packers/Green Bay Packers"),
        ListItem(7, "New York Giants", "🏆 4 Super Bowls", route = "detail/giants_nfl/New York Giants"),
        ListItem(8, "Kansas City Chiefs", "🏆 3 Super Bowls", route = "detail/chiefs/Kansas City Chiefs"),
        ListItem(9, "Las Vegas Raiders", "🏆 3 Super Bowls", route = "detail/raiders/Las Vegas Raiders"),
        ListItem(10, "Washington Commanders", "🏆 3 Super Bowls", route = "detail/commanders/Washington"),
        ListItem(11, "Miami Dolphins", "🏆 2 Super Bowls", route = "detail/dolphins/Miami Dolphins"),
        ListItem(12, "Indianapolis Colts", "🏆 2 Super Bowls", route = "detail/colts/Indianapolis Colts"),
        ListItem(13, "Tampa Bay Buccaneers", "🏆 2 Super Bowls", route = "detail/bucs/Tampa Bay Bucs"),
        ListItem(14, "Playoffs 2024", type = ItemType.ACTION, route = "nfl_playoffs")
    )

    fun getSportsMenu(): List<ListItem> = listOf(
        ListItem(1, "🏆 Sports Hub", type = ItemType.HEADER),
        ListItem(2, "⚽ Champions League", "Equipos europeos de elite", route = "champions"),
        ListItem(3, "🇪🇸 La Liga", "Primera División Española", route = "laliga"),
        ListItem(4, "🏴󠁧󠁢󠁥󠁮󠁧󠁿 Premier League", "Liga Inglesa", route = "premier"),
        ListItem(5, "🏀 NBA", "National Basketball Association", route = "nba"),
        ListItem(6, "⚾ MLB", "Major League Baseball", route = "mlb"),
        ListItem(7, "🏈 NFL", "National Football League", route = "nfl"),
        ListItem(8, "Configuración", type = ItemType.ACTION, route = "settings"),
        ListItem(9, "Favoritos", "⭐ Equipos marcados", route = "favorites"),
        ListItem(10, "Noticias deportivas", "📰 Últimas noticias", route = "sports_news"),
        ListItem(11, "Acerca de", type = ItemType.ACTION, route = "about")
    )

    fun getTeamDetails(teamId: String): Map<String, String> = when(teamId.lowercase()) {
        "real_madrid", "real_madrid_liga" -> mapOf(
            "name" to "Real Madrid",
            "league" to "La Liga / Champions League",
            "founded" to "1902",
            "stadium" to "Santiago Bernabéu",
            "capacity" to "81,044",
            "coach" to "Carlo Ancelotti",
            "titles" to "35 La Liga, 14 Champions League",
            "colors" to "⚪ Blanco"
        )
        "barcelona", "barcelona_liga" -> mapOf(
            "name" to "FC Barcelona",
            "league" to "La Liga / Champions League",
            "founded" to "1899",
            "stadium" to "Camp Nou",
            "capacity" to "99,354",
            "coach" to "Xavi Hernández",
            "titles" to "27 La Liga, 5 Champions League",
            "colors" to "🔴🔵 Azulgrana"
        )
        "lakers" -> mapOf(
            "name" to "Los Angeles Lakers",
            "league" to "NBA - Western Conference",
            "founded" to "1947",
            "arena" to "Crypto.com Arena",
            "capacity" to "20,000",
            "coach" to "Darvin Ham",
            "titles" to "17 NBA Championships",
            "colors" to "🟡🟣 Purple & Gold"
        )
        "yankees" -> mapOf(
            "name" to "New York Yankees",
            "league" to "MLB - American League",
            "founded" to "1903",
            "stadium" to "Yankee Stadium",
            "capacity" to "47,309",
            "manager" to "Aaron Boone",
            "titles" to "27 World Series",
            "colors" to "⚪🔵 Navy & White"
        )
        else -> mapOf(
            "name" to "Equipo",
            "league" to "Liga",
            "founded" to "N/A",
            "stadium" to "Estadio",
            "capacity" to "N/A",
            "coach" to "Entrenador",
            "titles" to "Títulos",
            "colors" to "Colores"
        )
    }
}