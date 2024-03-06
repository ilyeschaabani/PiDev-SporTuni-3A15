package Controller;

import Entity.Competition;
import javafx.scene.control.Button;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class ButtonCellFactoryUser implements Callback<TreeTableColumn<Competition, Void>, TreeTableCell<Competition, Void>> {

    private final TreeTableView<Competition> tvc1;
    private final Callback<Competition, Void> deleteCallback;
    private final Callback<Competition, Void> editCallback;

    public ButtonCellFactoryUser(TreeTableView<Competition> tvc1, Callback<Competition, Void> deleteCallback, Callback<Competition, Void> editCallback) {
        this.tvc1 = tvc1;
        this.deleteCallback = deleteCallback;
        this.editCallback = editCallback;
    }

    @Override
    public TreeTableCell<Competition, Void> call(TreeTableColumn<Competition, Void> param) {
        return new TreeTableCell<>() {
            private final Button deleteButton = new Button("s'inscrire");
            private final Button editButton = new Button("view");

            {
                deleteButton.setOnAction(event -> {
                    Competition item = getTreeTableRow().getItem();
                    deleteCallback.call(item);
                });

                editButton.setOnAction(event -> {
                    Competition item = getTreeTableRow().getItem();
                    editCallback.call(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(deleteButton, editButton));
                }
            }
        };
    }
}

