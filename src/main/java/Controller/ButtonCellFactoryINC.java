package Controller;

import Entity.InscriptionComp;
import javafx.scene.control.Button;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class ButtonCellFactoryINC implements Callback<TreeTableColumn<InscriptionComp, Void>, TreeTableCell<InscriptionComp, Void>> {

    private final TreeTableView<InscriptionComp> tvc1;
    private final Callback<InscriptionComp, Void> deleteCallback;
    private final Callback<InscriptionComp, Void> editCallback;

    public ButtonCellFactoryINC(TreeTableView<InscriptionComp> tvc1, Callback<InscriptionComp, Void> deleteCallback, Callback<InscriptionComp, Void> editCallback) {
        this.tvc1 = tvc1;
        this.deleteCallback = deleteCallback;
        this.editCallback = editCallback;
    }

    @Override
    public TreeTableCell<InscriptionComp, Void> call(TreeTableColumn<InscriptionComp, Void> param) {
        return new TreeTableCell<>() {
            private final Button deleteButton = new Button("Supprimer");
            private final Button editButton = new Button("Modifier");

            {
                deleteButton.setOnAction(event -> {
                    InscriptionComp item = getTreeTableRow().getItem();
                    deleteCallback.call(item);
                });

                editButton.setOnAction(event -> {
                    InscriptionComp item = getTreeTableRow().getItem();
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
