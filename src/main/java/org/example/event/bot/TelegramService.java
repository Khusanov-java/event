package org.example.event.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.example.event.entity.Event;
import org.example.event.entity.User;
import org.example.event.repo.EventRepository;
import org.example.event.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TelegramService {
    private final Map<Long, TgUser> userStates = new HashMap<>();

    private final TelegramBot telegramBot;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;


    public void handle(Update update) {
        try {
            if (update.message() != null) {
                List<Event> all = eventRepository.findAll();
                Long chatId = update.message().chat().id();
                User user = new User();
                String text = update.message().text();
                Contact contact = update.message().contact();
                // get existing user or create new
                TgUser tgUser = userStates.getOrDefault(chatId, new TgUser());

                if (text != null && text.equals("/start")) {
                    SendMessage sendMessage = new SendMessage(chatId, "Assalomu aleykum. Iltimos raqamingizni yuboring!");
                    sendMessage.replyMarkup(generateContactButton());
                    telegramBot.execute(sendMessage);
                    tgUser.setState(State.SHARE_CONTACT);
                    userStates.put(chatId, tgUser);
                } else if (contact != null && tgUser.getState() == State.SHARE_CONTACT) {
                    String phone = contact.phoneNumber().startsWith("+") ? contact.phoneNumber() : "+" + contact.phoneNumber();
                    tgUser.setPhone(phone);
                    System.out.println(contact);
                    user = userRepository.findByPhone(phone);
                    System.out.println(user);
                    SendMessage sendMessage = new SendMessage(chatId, "Lokatsiyani yuboring!");
                    sendMessage.replyMarkup(generateLocationButton());
                    telegramBot.execute(sendMessage);
                    tgUser.setState(State.SHARE_LOCATION);
                    userStates.put(chatId, tgUser);
                } else if (update.message().location() != null && tgUser.getState() == State.SHARE_LOCATION) {
                    Double latitude = Double.valueOf(update.message().location().latitude());
                    Double longitude = Double.valueOf(update.message().location().longitude());
                    user.setLongitude(longitude);
                    user.setLatitude(latitude);
                    SendMessage sendMessage = new SendMessage(chatId, "Rahmat! Hammasi muvaffaqiyatli qabul qilindi. Tanlang");
                    sendMessage.replyMarkup(generateMenuButton());
                    telegramBot.execute(sendMessage);
                    tgUser.setState(State.MENU);
                    userStates.put(chatId, tgUser);
                }
                else if (text != null && tgUser.getState() == State.MENU) {
                    System.out.println("yaxshi");
                    if (text.equals("Booking")){
                        System.out.println("zor");
                        StringBuilder messageText = new StringBuilder("Eventlar:\n");
                        for (Event event : all) {
                            messageText.append("• ").append(event.getTitle()).append("\n");
                        }
                        InlineKeyboardMarkup markup = buildEventListInlineKeyboard(all);
                        SendMessage sendMessage = new SendMessage(chatId, messageText.toString());
                        sendMessage.replyMarkup(markup);
                        telegramBot.execute(sendMessage);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private InlineKeyboardMarkup buildEventListInlineKeyboard(List<Event> events) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        for (Event event : events) {
            InlineKeyboardButton eventButton = new InlineKeyboardButton(event.getTitle());
            InlineKeyboardButton bookButton = new InlineKeyboardButton("Book")
                    .callbackData(event.getId().toString());
            markup.addRow(eventButton, bookButton);
        }

        return markup;
    }

    private Keyboard generateMenuButton() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup("");
        replyKeyboardMarkup.addRow(
                new KeyboardButton("Booking"),
                new KeyboardButton("Yaqindagi eventlar"),
                new KeyboardButton("Event qo'shish")
        ).resizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboardMarkup getAsosiyMenyu() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton("Asosiy menyu")
        );
    }

    private static Keyboard generateLocationButton() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton("Lokatsiya yuborish").requestLocation(true)
        ).resizeKeyboard(true);
    }

    private static Keyboard generateContactButton() {
        return new ReplyKeyboardMarkup(
                new KeyboardButton("Raqamni yuborish").requestContact(true)
        ).resizeKeyboard(true);
    }
}