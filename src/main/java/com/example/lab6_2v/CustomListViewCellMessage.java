package com.example.lab6_2v;

import domain.Message;
import domain.MessageDTO;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class CustomListViewCellMessage extends ListCell<MessageDTO> {
    private VBox content;
    private Text from;
    private Text date;
    private Text text;

    public CustomListViewCellMessage(){
        super();
        from = new Text();
        from.setFill(Paint.valueOf("grey"));
        from.setOpacity(50);
        date = new Text();
        date.setFill(Paint.valueOf("grey"));
        date.setOpacity(50);
        HBox hBox = new HBox(from, date);
        hBox.setSpacing(150);
        text = new Text();
        content = new VBox(hBox, text);
        content.setSpacing(10);
    }

    @Override
    protected void updateItem(MessageDTO item, boolean empty) {
        super.updateItem(item, empty);
        if(item != null && !empty){
            from.setText(item.getFrom());
            date.setText(item.getDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)));
            text.setText(item.getText());
            setGraphic(content);
        }
        else{
            setGraphic(null);
        }
    }
}
