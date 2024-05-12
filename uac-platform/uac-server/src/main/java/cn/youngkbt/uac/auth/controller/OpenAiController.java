package cn.youngkbt.uac.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiImageClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author Kele-Bingtang
 * @date 2024/5/8 23:30:04
 * @note
 */
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class OpenAiController {
    private final OpenAiChatClient chatClient;
    private final OpenAiImageClient imageClient;

    @GetMapping("/chat")
    public String generate(@RequestParam(value = "message", defaultValue = "告诉我一个笑话") String message) {
        return chatClient.call(message);
    }

    @GetMapping(value = "/ai/chatStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "告诉我一个笑话") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatClient.stream(prompt);
    }

    @GetMapping(value = "/image", produces = MediaType.TEXT_HTML_VALUE)
    public String generateImage(String prompt) {
        ImageResponse call = imageClient.call(new ImagePrompt(prompt));
        return "<img src=" + call.getResult().getOutput().getUrl() + " />";
    }
}
