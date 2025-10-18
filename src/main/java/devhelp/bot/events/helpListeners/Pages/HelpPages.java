package devhelp.bot.events.helpListeners.Pages;

import java.util.HashMap;
import java.util.Map;

import devhelp.bot.config.util.Colors;
import devhelp.bot.config.util.EmbedTemplate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

public class HelpPages implements IPages {
  private static final Map<String, MessageEmbed> pages = new HashMap<>();

  static {
    pages.put("menu", new EmbedTemplate().embedInfo(null,
        """
              ## 📖 Central de Ajuda – Star Code
              Bem-vindo à central de ajuda do Star Code!
              Aqui você encontra todos os comandos disponíveis para explorar, aprender e se divertir na comunidade.

              **✨ Categorias de comandos:**

              **📚 Estudos**
              Comandos voltados para aprendizado e prática de programação.

              **🕹️ Diversão**
              Comandos interativos para relaxar e se conectar com a comunidade.

              **🧑‍💻 Iniciante**
              Recursos e atalhos para quem está começando na jornada dev.

              **🎯 Utilidades**
              Ferramentas úteis para facilitar seu dia a dia como desenvolvedor.
            """, "Selecione uma categoria para ver os comandos disponíveis."));
    pages.put("Estudos", new EmbedTemplate().embedInfo(null,
        """
            # 📚 Comandos de Estudos
            > Aqui estão os comandos para estudos:
            ``/exercise`` - Comando para gerar exercícios de programação!
            """, null));
    pages.put("Diversão", new EmbedTemplate().embedInfo(null,
        """
            # 🕹️ Comandos de Diversão
            > Aqui estão os comandos para diversão:
            ``/memes`` - Comando para gerar memes aleatório de programação!
            ``/coinflip`` - Comando para girar a moeda!
            """, null));
    pages.put("Iniciante", new EmbedTemplate().embedInfo(null,
        """
            # 🧑‍💻 Comandos para Iniciante
            > Aqui estão os comandos para iniciante:
            ``/languages`` - Comando para exibir informações sobre linguagens de programação!
            ``/roadmap`` - Comando para exibir roadmaps de aprendizado!
            ``/git`` - Comando para exibir comandos git!
            """, null));
    pages.put("Utilidades", new EmbedTemplate().embedInfo(null,
      """
          # 🎯 Comandos de Utilidades
          > Aqui estão os comandos de utilidades:
          ``/profile`` - Comando para exibir informações e alterar dados do perfil!
          ``/github`` - Comando para para exibir a Streak de commits do GitHub!
          ``/level`` - Comando para exibir o level e o xp do(s) usuário(s)!
          ``/rank`` - Comando para exibir o top rank do(s) usuário(s)!
      """,
    null));
  }

  public static MessageEmbed getPage(String key) {
    return pages.getOrDefault(key, errorPage());
  }

  private static MessageEmbed errorPage() {
    return new EmbedTemplate().embedError("❌ Erro", "Opção inválida!", null);
  }

  public static StringSelectMenu getSelectHelpMenu(String selected) {
    return StringSelectMenu.create("command-menu")
        .addOptions(
          SelectOption.of("Estudos 📚", "Estudos")
            .withDescription("Acesse comandos voltados para aprendizado")
            .withDefault("Estudos".equals(selected)),
          SelectOption.of("Diversão 🕹️", "Diversão")
            .withDescription("Descubra comandos para se divertir")
            .withDefault("Diversão".equals(selected)),
          SelectOption.of("Iniciante 👨‍💻", "Iniciante")
            .withDescription("Obtenha orientações para começar na programação")
            .withDefault("Iniciante".equals(selected)),
          SelectOption.of("Utilidades 🎯", "Utilidades")
            .withDescription("Explore ferramentas úteis para desenvolvedores")
            .withDefault("Utilidades".equals(selected)),
          SelectOption.of("Menu 💾", "Menu")
            .withDescription("Volte ao menu principal")
            .withDefault("Menu".equals(selected))
        ).build();
  }

  public static void optionsHelp(StringSelectInteractionEvent event){
      String choice = event.getValues().get(0);
      switch (choice) {
      case "Estudos" -> event.editMessageEmbeds(
        getPage("Estudos")
      ).setComponents(
        ActionRow.of(
          getSelectHelpMenu(choice)
        ),
        ActionRow.of(
          StudyPages.getSelectStudyMenu()
        )
      ).queue();
      case "Diversão" -> event.editMessageEmbeds(getPage("Diversão")).setComponents(
        ActionRow.of(
          getSelectHelpMenu(choice)
        ),
        ActionRow.of(
          FunPages.getSelectFunMenu(choice)
        )
      ).queue();
      case "Iniciante" -> event.editMessageEmbeds(getPage("Iniciante")).setComponents(
          ActionRow.of(
            getSelectHelpMenu(choice)
          ),
          ActionRow.of(
          )
      ).queue();
      case "Utilidades" -> event.editMessageEmbeds(getPage("Utilidades")).setComponents(
        ActionRow.of(
          getSelectHelpMenu(choice)
        ),
        ActionRow.of(
          UtilityPages.getSelectUtilityMenu(choice)
        )
      ).queue();
      case "Menu" -> event.editMessageEmbeds(
          HelpPages.getPage("menu")
      ).setComponents(
        ActionRow.of(
          HelpPages.getSelectHelpMenu(choice)
        )
      ).queue();
      default -> event.editMessageEmbeds(new EmbedBuilder()
          .setTitle("Erro")
          .setDescription("Opção inválida!")
          .setColor(Colors.getError())
          .build()).queue();
    }
  }
}
