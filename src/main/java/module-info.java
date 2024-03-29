module fr.til.projetfilrouge.mailspamdetectorproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires junit;
    requires java.mail;
    opens fr.til.projetfilrouge.mailspamdetectorproject to javafx.fxml;
    exports fr.til.projetfilrouge.mailspamdetectorproject;
    exports fr.til.projetfilrouge.mailspamdetectorproject.Test;
    exports fr.til.projetfilrouge.mailspamdetectorproject.Model;
    opens fr.til.projetfilrouge.mailspamdetectorproject.Model to javafx.fxml;
    exports fr.til.projetfilrouge.mailspamdetectorproject.Controller;
    exports fr.til.projetfilrouge.mailspamdetectorproject.View;
    opens fr.til.projetfilrouge.mailspamdetectorproject.View to javafx.fxml;
    opens fr.til.projetfilrouge.mailspamdetectorproject.Controller to javafx.fxml;
}