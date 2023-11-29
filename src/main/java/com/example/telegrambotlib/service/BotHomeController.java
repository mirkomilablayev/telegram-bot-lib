package com.example.telegrambotlib.service;

import com.example.telegrambotlib.configuration.BotUpdateHandler;
import com.example.telegrambotlib.easybot.annotation.BotController;
import com.example.telegrambotlib.easybot.annotation.HandleMessage;
import com.example.telegrambotlib.easybot.annotation.HandleUndefined;
import com.example.telegrambotlib.easybot.annotation.HandleUserStep;
import com.example.telegrambotlib.entity.User;
import com.example.telegrambotlib.util.Steps;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@BotController
@Service
@AllArgsConstructor
public class BotHomeController {

    private final BotUpdateHandler botUpdateHandler;

    @HandleMessage("/start")
    public void start(Update update, User user) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getChatId());
        sendMessage.setText("/start");
        botUpdateHandler.sendMessageExecutor(sendMessage);
    }

    @HandleUserStep(Steps.ASK_CONTACT)
    public void registeredPage(Update update, User user) throws TelegramApiException {

    }

    @HandleUndefined
    public void undefined(Update update, User user) {

    }


}
