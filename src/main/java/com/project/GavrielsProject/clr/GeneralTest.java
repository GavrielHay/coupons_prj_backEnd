package com.project.GavrielsProject.clr;

import com.project.GavrielsProject.utils.ArtUtils.Banners;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.project.GavrielsProject.utils.ArtUtils.FontDesign.ConsoleColors.*;

//@Component
@RequiredArgsConstructor
@Order(1)
public class GeneralTest implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        System.out.println(".\n.\n.\n.\n.\n.\n" + RED_BRIGHT + "* * * * * * * * * * ALL WHITE MESSAGES ARE SYSTEM MESSAGES. THE OTHERS ARE MINE * * * * * * * * * * \n" + RESET);
        System.out.println(Banners.localhost);
        System.out.println(GREEN_UNDERLINED + "\n\n\nDaily job (old coupons delete) is automatically running" + RESET);



    }
}
