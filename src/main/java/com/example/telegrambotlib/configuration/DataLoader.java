package com.example.telegrambotlib.configuration;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@RequiredArgsConstructor
@Component
@Slf4j
public class DataLoader implements CommandLineRunner {


    private final BotUpdateHandler botUpdateHandler;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) throws Exception {
        
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this.botUpdateHandler);

        if (ddl.equalsIgnoreCase("create")
                || ddl.equalsIgnoreCase("create-drop")) {
            System.out.println();
            log.info("------------------------------------");
        }


    }


}
