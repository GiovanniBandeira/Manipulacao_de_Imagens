import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ChromaKey {
    public static void main(String[] args) throws IOException {

        String img = "src\\ImagensFontes\\chromaKey.jpg";
        BufferedImage imagemOriginal = ImageIO.read(new File(img));

        int w = imagemOriginal.getWidth();
        int h = imagemOriginal.getHeight();

        BufferedImage imagem = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // Aplicação do Chroma Key
        for (int lin = 0; lin < w; lin++) {
            for (int col = 0; col < h; col++) {

                int rgb = imagemOriginal.getRGB(lin, col);
                Color color = new Color(rgb, true);

                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                // Cor de fundo da imagem (64, 254, 0)
                // Declarei essas cores para conseguir remover o máximo do chorma que puder (<=100 e >= 150)
                // Caso queira retirar outra cor, será necessario a modificação do RBG do if abaixo
                if (red <= 100 && green >= 150) {
                    imagem.setRGB(lin, col, new Color(0, 0, 0, 0).getRGB());
                } else {
                    imagem.setRGB(lin, col, rgb);
                }
            }
        }

//===============================================================================================

        // Geração de novo nome de arquivo
        String srcFolder = "src\\ImagensGeradas\\ChromaKey\\";
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
