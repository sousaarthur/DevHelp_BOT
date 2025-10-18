package devhelp.bot.events.helpListeners.Pages;

import java.util.HashMap;
import java.util.Map;

import devhelp.bot.config.util.EmbedTemplate;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

public class FunPages implements IPages {
  private static final Map<String, MessageEmbed> pages = new HashMap<>();
  static{
    pages.put("memes", new EmbedTemplate().embedInfo(null,
        """
            # Comando de Memes
            > Aqui estão os comandos para memes:
            ``/memes`` - **Comando para gerar memes aleatório de programação!**
            Caso queira um meme novo, basta usar o comando novamente. Alem disso, o meme possui dois botões: 
            > **Like**: para curtir o meme.
            > **Views**: para ver quantas vezes o meme foi visualizado.
            """, null));
    pages.put("coinflip", new EmbedTemplate().embedInfo(null,
        """
            # Comando de Coinflip
            > Aqui estão os comandos para coinflip:
            ``/coinflip`` - **Comando para girar a moeda!**
            Caso queira girar a moeda novamente, basta usar o comando novamente. Alem disso, o coinflip possui dois botões: 
            """, null));
  }

  public static MessageEmbed getPage(String key) {
      return pages.getOrDefault(key, errorPage());
  }

  private static MessageEmbed errorPage() {
      return new EmbedTemplate().embedError("❌ Erro", "Opção inválida!", null);
  }

  public static StringSelectMenu getSelectFunMenu(String selected){
    try{
      return StringSelectMenu.create("fun-menu")
      .setPlaceholder("Selecione um comando!")
      .addOption("Memes", "memes", "Acesse comandos voltados para memes.")
      .addOption("Coinflip", "coinflip", "Acesse comandos sobre o coinflip.")
      .build();
    } catch (Exception e){
      throw new RuntimeException(e);
    }
  }

  public static void optionsFun(StringSelectInteractionEvent event){
    String choice = event.getValues().get(0);
    switch (choice) {
      case "memes" -> createEmbedPage(event, choice);
      case "coinflip" -> createEmbedPage(event, choice);
      default -> event.editMessageEmbeds(
        new EmbedTemplate().embedError("❌ Erro", "Opção inválida!", null)
      ).queue();
    }
  }

  public static void createEmbedPage(StringSelectInteractionEvent event, String choice){
    event.editMessageEmbeds(
        getPage(choice)
      ).setComponents(
        ActionRow.of(
          HelpPages.getSelectHelpMenu("Diversão")
        ),
        ActionRow.of(getSelectFunMenu(choice))
      ).queue();
  }
}
