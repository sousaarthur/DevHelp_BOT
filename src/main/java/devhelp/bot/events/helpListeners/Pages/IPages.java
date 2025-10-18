package devhelp.bot.events.helpListeners.Pages;

import java.util.HashMap;
import java.util.Map;

import devhelp.bot.config.util.EmbedTemplate;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;

public interface IPages {
  static final Map<String, MessageEmbed> pages = new HashMap<>();

  static MessageEmbed getPage(String key) {
    return pages.getOrDefault(key, erroPage());
  }

  static MessageEmbed erroPage() {
    return new EmbedTemplate().embedError("❌ Erro", "Opção invalida!", null);
  }

  static void createEmbedPage(StringSelectInteractionEvent event, String choice){
    event.editMessageEmbeds(
        getPage(choice)
      ).setComponents(
        ActionRow.of(
          HelpPages.getSelectHelpMenu(null)
        ),
        ActionRow.of()
      ).queue();
  }
}
