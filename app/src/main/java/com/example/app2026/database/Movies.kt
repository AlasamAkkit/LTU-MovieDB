package com.example.app2026.database

import com.example.app2026.models.Movie

class Movies {
    fun getMovies(): List<Movie>{
        return listOf(
            Movie(
                9806,
                "The Incredibles",
                "/2LqaLgk4Z226KkgPJuiOQ58wvrm.jpg",
                "/8eRscFbRYl681zDfkjv1jjW1KAA.jpg",
                "2004-10-27",
                "Bob Parr has given up his superhero days to log in time as an insurance adjuster and raise his three children with his formerly heroic wife in suburbia. But when he receives a mysterious assignment, it's time to get back into costume.",
                listOf("Drama", "Action"),
                "https://www.pixar.com/the-incredibles",
                "tt0317705"
            ),
            Movie(
                76600,
                "Avatar: The Way of Water",
                "/3C5brXxnBxfkeKWwA1Fh4xvy4wr.jpg",
                "/6qkJLRCZp9Y3ovXti5tSuhH0DpO.jpg",
                "2022-12-14",
                "Set more than a decade after the events of the first film, learn the story of the Sully family (Jake, Neytiri, and their kids), the trouble that follows them, the lengths they go to keep each other safe, the battles they fight to stay alive, and the tragedies they endure.",
                listOf("Adventure", "Action", "Science Fiction"),
                "https://www.avatar.com/movies/avatar-the-way-of-water",
                "tt1630029"
            ),
            Movie(
                667216,
                "Infinity Pool",
                "/cQKyNm0Nz6KWiDBQF9w4aZAmOfC.jpg",
                "/uAfPjNvfHjFuvdtSa8J3iuD1CkI.jpg",
                "2023-01-27",
                "While staying at an isolated island resort, James and Em are enjoying a perfect vacation of pristine beaches, exceptional staff, and soaking up the sun. But guided by the seductive and mysterious Gabi, they venture outside the resort grounds and find themselves in a culture filled with violence, hedonism, and untold horror. A tragic accident leaves them facing a zero tolerance policy for crime: either you'll be executed, or, if you’re rich enough to afford it, you can watch yourself die instead.",
                listOf("Horror", "Science Fiction", "Thriller"),
                "https://neonrated.com/films/infinity-pool",
                "tt10365998"
            ),
            Movie(
                502356,
                "The Super Mario Bros. Movie",
                "/qNBAXBIQlnOThrVvA6mA2B5ggV6.jpg",
                "/nDxJJyA5giRhXx96q1sWbOUjMBI.jpg",
                "2023-04-05",
                "While working underground to fix a water main, Brooklyn plumbers—and brothers—Mario and Luigi are transported down a mysterious pipe and wander into a magical new world. But when the brothers are separated, Mario embarks on an epic quest to find Luigi.",
                listOf("Comedy", "Family", "Adventure", "Fantasy", "Animation"),
                "https://www.uphe.com/movies/the-super-mario-bros-movie",
                "tt6718170"
            ),
            Movie(
                640146,
                "Ant-Man and the Wasp: Quantumania",
                "/ngl2FKBlU4fhbdsrtdom9LVLBXw.jpg",
                "/iJQIbOPm81fPEGKt5BPuZmfnA54.jpg",
                "2023-02-15",
                "Super-Hero partners Scott Lang and Hope van Dyne, along with with Hope's parents Janet van Dyne and Hank Pym, and Scott's daughter Cassie Lang, find themselves exploring the Quantum Realm, interacting with strange new creatures and embarking on an adventure that will push them beyond the limits of what they thought possible.",
                listOf("Action", "Adventure", "Science Fiction"),
                "https://www.marvel.com/movies/ant-man-and-the-wasp-quantumania",
                "tt10954600"
            ),
            Movie(
                1570282,
                "This is I",
                "/3aMYB238ZU2GPjP7YAvcIzPjQaN.jpg",
                "/3DOOgeZfoYUKXAyYrpRObv2PNDd.jpg",
                "2026-02-10",
                "Bullied for wanting to be an idol, Kenji finds belonging in a cabaret and help from a trailblazing doctor, emerging onstage as her true self, Ai Haruna.",
                listOf("Drama", "Music"),
                "https://www.netflix.com/title/81774276",
                "tt39196792"
            )
        )
    }
}