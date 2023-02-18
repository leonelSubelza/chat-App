package com.involveInovation.chatServer.controller;

import com.involveInovation.chatServer.controller.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    //Cualquier usuario que se quiera conectar a este controlador tendrá que establcer la URL con /app/Loquesea


    //Para enviar mensajes a alguien en privado
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    //la anotación @Payload se utiliza para indicar qué parámetro o campo de una clase es
    // el cuerpo de un mensaje, lo que permite que Spring convierta automáticamente el contenido del
    // mensaje en un objeto Java del tipo adecuado.
    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){
        return message;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message){
        //El metodo convertAndSendToUser detecta automaticamente el
        // "prefijo de destino del usuario" que se seteo en el metodo
        //configureMessageBroker() como /user
        //ConvertAndSendToUser, getReciverName => prefijo que quiero escuchar,
        // /private se le añade al prefijo el /private y luego se pone el obj que se recibe
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println(message.toString());
        //El cliente para conectarse deberá establcer una URL de tipo /user/David/private
        return message;
    }
}
