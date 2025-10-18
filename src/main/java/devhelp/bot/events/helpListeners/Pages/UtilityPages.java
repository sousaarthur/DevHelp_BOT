package devhelp.bot.events.helpListeners.Pages;

import java.util.HashMap;
import java.util.Map;

import devhelp.bot.config.util.EmbedTemplate;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

public class UtilityPages implements IPages{
  private static Map<String, MessageEmbed> pages = new HashMap<>();

  static{
    pages.put("profile", new EmbedTemplate().embedInfo(null,
        """
            # Comando de Profile
            > Aqui estão os comandos para profile:
            ``/profile`` - **Comando para exibir informações e alterar dados do perfil!**
            Você pode visualizar e atualizar seu perfil de desenvolvedor, incluindo nome, bio e avatar.
            """, null));
  }

  public static MessageEmbed getPage(String key){
    return pages.getOrDefault(key, erroPage());
  }

  private static MessageEmbed erroPage() {
    return new EmbedTemplate().embedError("❌ Erro", "Opção invalida!", null);
  }

  public static StringSelectMenu getSelectUtilityMenu(String selected) {
    try{
      return StringSelectMenu.create("utility-menu")
      .setPlaceholder("Selecione um comando!")
      .addOption("Profile", "profile", "Acesse comandos voltados para profile.")
      .build();
    } catch (Exception e){
      throw new RuntimeException(e);
    }
  }

    public static void optionsUtility(StringSelectInteractionEvent event){
    String choice = event.getValues().get(0);
    switch (choice) {
      case "profile" -> createEmbedPage(event, choice);
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
        HelpPages.getSelectHelpMenu("Utilidades")
      ),
      ActionRow.of(getSelectUtilityMenu(choice))
    ).queue();
  }
}
