module fr.til.projetfilrouge.mailspamdetectorproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires junit;

    opens fr.til.projetfilrouge.mailspamdetectorproject to javafx.fxml;
    exports fr.til.projetfilrouge.mailspamdetectorproject;
    exports fr.til.projetfilrouge.mailspamdetectorproject.Tests;
    exports fr.til.projetfilrouge.mailspamdetectorproject.Model;
    opens fr.til.projetfilrouge.mailspamdetectorproject.Model to javafx.fxml;
    exports fr.til.projetfilrouge.mailspamdetectorproject.Controller;
}