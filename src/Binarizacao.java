import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Binarizacao {
    public static void main(String[] args) throws IOException {

        String imgPath = "src\\ImagensFontes\\imagem1.png";
        BufferedImage imagem = ImageIO.read(new File(imgPath));
        int w = imagem.getWidth();
        int h = imagem.getHeight();

        for (int lin = 0; lin < w; lin++) {
            for (int col = 0; col < h; col++) {
                int rgb = imagem.getRGB(lin, col);
                Color color = new Color(rgb);

                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                // Caso queira retirar outra  cor, será necessario modificar o rgb do if abaixo
                // Retirar o vermelho da imagem
                if (red >= 128 && green < 75 && blue < 75) {
                    imagem.setRGB(lin, col, Color.black.getRGB());
                }
            }
        }

//===============================================================================================

        // Geração de novo nome de arquivo
        String srcFolder = "src\\ImagensGeradas\\Binarizacao\\";
        File dir = new File(srcFolder);
        String baseName = "imagemNova";
        String extension = ".png";

        int count = 1;
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.getName().startsWith(baseName) && file.getName().endsWith(extension)) {
                String name = file.getName();
                String numberPart = name.substring(baseName.length(), name.length() - extension.length());
                try {
                    int num = Integer.parseInt(numberPart);
                    if (num >= count) {
                        count = num + 1;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        String newFileName = baseName + count + extension;
        File newImageFile = new File(srcFolder + newFileName);
        ImageIO.write(imagem, "png", newImageFile);
    }
}
