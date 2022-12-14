import java.awt.Color
import java.awt.Dimension
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.net.URL
import javax.swing.*
import javax.swing.text.BadLocationException
import javax.swing.text.DefaultHighlighter

fun main(args: Array<String>) {

    val frame = Window()
    frame.isVisible = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

}

class Window : JFrame() {
    val minSz = Dimension(400, 450)

    val regHref = Regex(
        "(?:\\b)(?:(?:https:\\/\\/)|(?:www.))([\\d\\w-]+)\\.(\\w{2,6})([\\/\\d\\w-_\\.=\\?%&\\\"]*)\\/?(?:\\b)",
        RegexOption.MULTILINE
    )

    val regEmail = Regex(
        "(?<!\\w)([\\w\\d_\\.\\-]+)@([\\w\\d_\\.\\-]+)\\.([a-z\\.]{2,6})(\\s)",
        RegexOption.MULTILINE
    )


    init {

        val textPane = JTextPane()
        textPane.apply{
            contentType = "text/html"
            text = URL("https://edu.kpfu.ru").readText()
        }
        val b = JButton("Find")
        b.addActionListener() {

            textPane.text = regEmail.replace(URL("https://edu.kpfu.ru").readText()) {
                "${it.value}"
            }
            textPane.text = regHref.replace(URL("https://edu.kpfu.ru").readText()) {
                "${it.value}"
            }

            /*regEmail.findAll(URL("https://edu.kpfu.ru").readText()).forEach {
                try {
                    textPane.getHighlighter().addHighlight(
                        it.range.first, it.range.last,
                        DefaultHighlighter.DefaultHighlightPainter(Color.RED)
                    )
                } catch (e: BadLocationException) {
                    e.printStackTrace()
                }
            }

            regHref.findAll(URL("https://edu.kpfu.ru").readText()).forEach {
                try {
                    textPane.getHighlighter().addHighlight(
                        it.range.first, it.range.last,
                        DefaultHighlighter.DefaultHighlightPainter(Color.RED)
                    )
                } catch (e: BadLocationException) {
                    e.printStackTrace()
                }
            }
            */

        }

        val scrPane = JScrollPane(textPane)


        minimumSize = minSz

        layout = GroupLayout(contentPane).apply {
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(8)
                    .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(scrPane, 350, 1080, GROW)
                            .addComponent(b, SHRINK, SHRINK, SHRINK)
                    )
                    .addGap(8)
            )

            setVerticalGroup(
                createSequentialGroup()
                    .addGap(8)
                    .addComponent(scrPane, 350, 1920, GROW)
                    .addGap(8)
                    .addComponent(b, SHRINK, SHRINK, SHRINK)
                    .addGap(8)
            )
        }
    }

    companion object {
        const val GROW = GroupLayout.DEFAULT_SIZE
        const val SHRINK = GroupLayout.PREFERRED_SIZE
    }
}