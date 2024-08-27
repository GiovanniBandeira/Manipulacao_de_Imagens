import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class AddFundo {
    public static void main(String[] args) throws IOException {

        String img = "src\\ImagensFontes\\carro.png";
        String imgf = "src\\ImagensFontes\\imagemFundo.jpg";
        BufferedImage imagemOriginal = ImageIO.read(new File(img));
        BufferedImage imagemFundo = ImageIO.read(new File(imgf));

        int w = imagemFundo.getWidth();
        int h = imagemFundo.getHeight();

        System.out.printf("%d %d", w, h);

        // Serve para implementar o Alpha na função Color
        BufferedImage imagem = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        
        // Aplicação da imagem PNG sobre a outra
        for (int lin = 0; lin < w; lin++) {
            for (int col = 0; col < h; col++) {

                int rgb = imagem.getRGB(lin, col);
                Color color = new Color(rgb, true);

                imagem.setRGB(lin, col, imagemFundo.getRGB(lin, col));

                // Coordena a posição desejada para aplicação da imagem e verifica se a imagem permanece dentro do limite
                //da Imagem de Fundo.
                // Ao trocar de imagem que irar sobrepor a outra, será necessario adaptar a coordenada do if abaixo;
                if (lin >= 606 && col >= 374 && lin < 606 + imagemOriginal.getWidth() && col < 374 + imagemOriginal.getHeight()) {

                    // Encremetação de uma nova cor para verificar posteriomente se a imagem é vazia ou não
                    int rgb1 = imagemOriginal.getRGB(lin - 606, col - 374);
                    Color color1 = new Color(rgb1, true);

                    // Verifica se o pixel da imagem não é transparente
                    if (color1.getAlpha() != 0) {
                        imagem.setRGB(lin, col, rgb1);
                    }
                }

            }
        }


//===============================================================================================

        // Geração de novo nome de arquivo
        String srcFolder = "src\\ImagensGeradas\\AddFundo\\";
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
