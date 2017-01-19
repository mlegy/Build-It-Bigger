
package com.example;

public class JokeTeller {
    private String[] jokes = {
            "I dreamt I was forced to eat a giant marshmallow." +
                    " When I woke up, my pillow was gone.",
            "Doctor: \"I'm sorry but you suffer from a terminal illness and have only 10 to live." +
                    "\"\nPatient: \"What do you mean, 10? 10 what? Months? Weeks?!" +
                    "\"\nDoctor: Nine",
            "My dog used to chase people on a bike a lot. It got so bad," +
                    " finally I had to take his bike away"
    };

    public String getJoke() {
        return jokes[(int) (Math.random() * 3)];
    }
}
