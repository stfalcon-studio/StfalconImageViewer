package com.stfalcon.sample.common.models

object Demo {

    private const val POSTERS_PATH = "https://raw.githubusercontent.com/stfalcon-studio/StfalconImageViewer/master/images/posters"
    private const val MISC_PATH = "https://raw.githubusercontent.com/stfalcon-studio/StfalconImageViewer/master/images/misc"

    val posters = listOf(
        Poster(url = "$POSTERS_PATH/Vincent.jpg", description = "Vincent Vega is a hitman and associate of Marsellus Wallace. He had a brother named Vic Vega who was shot and killed by an undercover cop while on a job. He worked in Amsterdam for over three years and recently returned to Los Angeles, where he has been partnered with Jules Winnfield."),
        Poster(url = "$POSTERS_PATH/Jules.jpg", description = "Jules Winnfield - initially he is a Hitman working alongside Vincent Vega but after revelation, or as he refers to it \"a moment of clarity\" he decides to leave to \"Walk the Earth.\" During the film he is stated to be from Inglewood, California"),
        Poster(url = "$POSTERS_PATH/Korben.jpg", description = "Korben Dallas. A post-America taxi driver in New York City with a grand military background simply lives his life day to day, that is, before he meets Leeloo. Leeloo captures his heart soon after crashing into his taxi cab one day after escaping from a government-run laboratory. Korben soon finds himself running from the authorities in order to protect Leeloo, as well as becoming the center of a desperate ploy to save the world from an unknown evil."),
        Poster(url = "$POSTERS_PATH/Toretto.jpg", description = "Dominic \"Dom\" Toretto is the brother of Mia Toretto, uncle to Jack and husband to Letty Ortiz. The protagonist in The Fast and the Furious franchise, Dominic is an elite street racer and auto mechanic."),
        Poster(url = "$POSTERS_PATH/Marty.jpg", description = "Martin Seamus \"Marty\" McFly Sr. - he is the world's second time traveler, the first to travel backwards in time and the first human to travel though time. He was also a high school student at Hill Valley High School in 1985. He is best friends with Dr. Emmett Brown, who unveiled his first working invention to him."),
        Poster(url = "$POSTERS_PATH/Driver.jpg", description = "The Driver - real name unknown - is a quiet man who has made a career out of stealing fast cars and using them as getaway vehicles in big-time robberies all over Los Angeles. Hot on the Driver's trail is the Detective (Bruce Dern), a conceited (and similarly nameless) cop who refers to the Driver as \"Cowboy\"."),
        Poster(url = "$POSTERS_PATH/Frank.jpg", description = "Frank Martin (Transporter) - he initially serves as a reluctant hero. He is portrayed as a former Special Forces operative who was a team leader of a search and destroy unit. His military background includes operations \"in and out of\" Lebanon, Syria and Sudan. He retires from this after becoming fatigued and disenchanted with his superior officers."),
        Poster(url = "$POSTERS_PATH/Max.jpg", description = "Maximillian \"Max\" Rockatansky started his apocalyptic adventure as a Main Force Patrol officer who fought for peace on the decaying roads of Australian civilization. Max served as the last line of defense against the reckless marauders terrorizing the roadways, driving a V8 Interceptor."),
        Poster(url = "$POSTERS_PATH/Daniel.jpg", description = "Daniel Morales - the fastest delivery man for the local pizza parlor Pizza Joe in Marseille, France. On the last day of work, he sets a new speed record, then leaves the job to pursue a new career as a taxi driver with the blessings of his boss and co-workers. Daniel's vehicle is a white 1997 Peugeot 406..."))

    val horizontalImages = listOf(
        "$MISC_PATH/horizontal_colorful_1.jpg",
        "$MISC_PATH/square_colorful.jpg",
        "$MISC_PATH/horizontal_colorful_2.jpg",
        "$MISC_PATH/horizontal_colorful_3.jpg"
    )

    val verticalImages = listOf(
        "$MISC_PATH/vertical_colorful_1.jpg",
        "$MISC_PATH/vertical_colorful_2.jpg",
        "$MISC_PATH/vertical_colorful_3.jpg",
        "$MISC_PATH/vertical_colorful_4.jpg"
    )
}