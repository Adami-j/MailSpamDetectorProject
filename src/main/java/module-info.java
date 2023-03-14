module fr.til.projetfilrouge.mailspamdetectorproject {
    requires javafx.controls;
    requires javafx.fxml;
    //requires junit;

    requires org.kordamp.bootstrapfx.core;

    opens fr.til.projetfilrouge.mailspamdetectorproject to javafx.fxml;
    exports fr.til.projetfilrouge.mailspamdetectorproject;
}