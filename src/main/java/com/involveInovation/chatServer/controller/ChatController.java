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

    //Cualquier usuario que se quiera conectar a este controlador tendrá que establecer la URL con /app/Loquesea


    //Para enviar mensajes a alguien en privado
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    //la anotación @Payload se utiliza para indicar qué parámetro o campo de una clase es
    // el cuerpo de un mensaje, lo que permite que Spring convierta automáticamente el contenido del
    // mensaje en un objeto Java del tipo adecuado.

    //Este método recibe msj del cliente, recibe todos los msj enviados por el cliente (si tiene)
    // y los reenvía a todos los demás clientes conectados/suscritos a /chatroom/public
    @MessageMapping("/message")
    //cuando el usuario envía un msj a /app/message, se enviará este msj a aquellos que estén suscritos a /chatroom/public
    @SendTo("/chatroom/public")//se especifica el subdestino al que se enviará el msj. El destino general es /chatroom y el subdestino es /public
    public Message receiveMessage(@Payload Message message){
        return message;
    }

    @MessageMapping("/private-message")
    // Cuando un usuario mande un msj a /app/private-message, este msj se enviará a quien esté suscrito a
    // /user/nombreReceptor/private. Cada usuario que se suscriba pro primera vez deberá poner su nombre en
    // esa url para recibir este msj priv
    public Message recMessage(@Payload Message message){
        //El método convertAndSendToUser detecta automáticamente el
        // "prefijo de destino del usuario" que se seteó en el método
        //configureMessageBroker()en el método registry.setUserDestinationPrefix("/user");

        //El método convertAndSendToUser() toma tres argumentos:
        // el nombre del usuario, el destino y el mensaje. En este caso,
        // se utiliza message.getReceiverName() como nombre de usuario para enviar el mensaje,
        // "/private" como destino y el propio objeto message como mensaje.
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println("Mensaje privado recibido de: "+message.getSenderName()+" para: "+message.getReceiverName());
        //El cliente para conectarse deberá establecer una URL de tipo /user/David/private
        return message;
    }


    @MessageMapping("/unsubscribe")
    @SendTo("/chatroom/disconnected")
    public Message unsubscribe(@Payload Message message) {
        return message;
    }
}
