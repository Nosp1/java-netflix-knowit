import static com.KnowItFlix.SerieType.Animasjon;
import static com.KnowItFlix.SerieType.Drama;
import static com.KnowItFlix.SerieType.Humor;
import static com.KnowItFlix.SerieType.Krim;
import static com.KnowItFlix.SerieType.Reality;
import static com.KnowItFlix.SerieType.TalkShow;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import com.KnowItFlix.Kunde;
import com.KnowItFlix.Serie;
import com.KnowItFlix.SerieRepository;

public class SerieRepositoryTest {

    private final SerieRepository SERIE_REPOSITORY = new SerieRepository();

    @Test
    public void skalLagreSerie() {
        //intialize
        Serie serie = new Serie("test", Animasjon, Drama);
        //arrange
        Integer id = SERIE_REPOSITORY.lagNySerie(serie.getTitle(), Animasjon, Drama);
        //assert
        assertThat(SERIE_REPOSITORY.getSerier()
            .get(0)
            .getSerieId()).isEqualTo(1);
        assertThat(SERIE_REPOSITORY
            .getSerier()
            .size())
            .isEqualTo(1);
        assertThat(
            SERIE_REPOSITORY
                .getSerier()
                .get(0)
                .getTitle())
            .isEqualTo(serie.getTitle());
        assertThat(
            SERIE_REPOSITORY
                .getSerier()
                .get(0)
                .
                    getType()
                .stream()
                .filter(type -> type.equals(Animasjon)));
        assertThat(
            SERIE_REPOSITORY
                .getSerier()
                .get(0)
                .getType()
                .stream()
                .filter(type -> type.equals(Drama)));
    }

    @Test
    public void skalFjerneSerieType() {
        Serie serie = new Serie("test", Drama, Animasjon);

        SERIE_REPOSITORY.lagNySerie("test", Drama, Animasjon);

        assertThat(SERIE_REPOSITORY.getSerier()
            .size()).isEqualTo(1);
        SERIE_REPOSITORY.fjernSerieType("test", "Drama");
        SERIE_REPOSITORY.fjernSerieType("test1", "TalkShow");
        assertThat(SERIE_REPOSITORY.getSerier()
            .get(0)
            .getType()
            .size()).isEqualTo(1);
        assertThat(SERIE_REPOSITORY.getSerier()
            .get(0)
            .getType()
            .get(0)).isEqualTo(Animasjon);

    }

    @Test
    public void skalleggeTilType() {
        SERIE_REPOSITORY.lagNySerie("test", Drama);
        assertThat(SERIE_REPOSITORY.getSerier()
            .get(0)
            .getType()
            .size()).isEqualTo(1);
        SERIE_REPOSITORY.leggTilType("test", Animasjon);
        assertThat(SERIE_REPOSITORY.getSerier()
            .get(0)
            .getType()
            .size()).isEqualTo(2);
        assertThat(SERIE_REPOSITORY.getSerier()
            .get(0)
            .getType()
            .get(1)).isEqualTo(Animasjon);

    }

    @Test
    public void skalLeggeTilNyKunde() {
        LocalDate date = LocalDate.now();

        Kunde kunde = new Kunde(1, "trym", date);
        SERIE_REPOSITORY.leggTilNyKunde("trym", date);
        assertThat(SERIE_REPOSITORY.getKunder()
            .size()).isEqualTo(1);
        assertThat(SERIE_REPOSITORY.getKunder()
            .get(0)
            .getId()).isEqualTo(kunde.getId());
        assertThat(SERIE_REPOSITORY.getKunder()
            .get(0)
            .getNavn()).isEqualTo(kunde.getNavn());
        assertThat(SERIE_REPOSITORY.getKunder()
            .get(0)
            .getDate()).isEqualTo(kunde.getDate());

    }

    @Test
    public void skalLeggeTilTypeForKunde() {
        SERIE_REPOSITORY.leggTilNyKunde("trym", LocalDate.now());
        SERIE_REPOSITORY.leggTilNyKunde("lisa", LocalDate.now());
        SERIE_REPOSITORY.leggTilTypeForKunde(1, Drama, Animasjon);
        SERIE_REPOSITORY.leggTilTypeForKunde(2, TalkShow);
        Kunde kunde = SERIE_REPOSITORY.getKunder()
            .get(0);
        assertThat(kunde.getInterestedIn()
            .size()).isEqualTo(2);
        assertThat(kunde.getInterestedIn()
            .get(0)).isEqualTo(Drama);
        assertThat(SERIE_REPOSITORY.getKunder()
            .get(1)
            .getInterestedIn()
            .size()).isEqualTo(1);
        assertThat(SERIE_REPOSITORY.getKunder()
            .get(1)
            .getInterestedIn()
            .get(0)).isEqualTo(TalkShow);

    }

    @Test
    public void skalFjerneSerietypeForKunde() {
        SERIE_REPOSITORY.leggTilNyKunde("trym", LocalDate.now());
        SERIE_REPOSITORY.leggTilNyKunde("lisa", LocalDate.now());
        SERIE_REPOSITORY.leggTilTypeForKunde(1, Drama, Animasjon);
        SERIE_REPOSITORY.leggTilTypeForKunde(2, TalkShow, Humor);

        SERIE_REPOSITORY.fjernTypeTilKunde(1, Drama, Animasjon);
        SERIE_REPOSITORY.fjernTypeTilKunde(2, Humor);

        assertThat(SERIE_REPOSITORY.getKunder()
            .get(0)
            .getInterestedIn()
            .size()).isEqualTo(0);
        assertThat(SERIE_REPOSITORY.getKunder()
            .get(1)
            .getInterestedIn()
            .size()).isEqualTo(1);
        assertThat(SERIE_REPOSITORY.getKunder()
            .get(1)
            .getInterestedIn()
            .get(0)).isEqualTo(TalkShow);

    }

    @Test
    public void hentKunderSomLikerSerie() {
        SERIE_REPOSITORY.lagNySerie("test", Animasjon, Drama, TalkShow, Humor);
        Integer trym = SERIE_REPOSITORY.leggTilNyKunde("trym", LocalDate.now());
        Integer lisa = SERIE_REPOSITORY.leggTilNyKunde("lisa", LocalDate.now());
        Integer jens = SERIE_REPOSITORY.leggTilNyKunde("jens", LocalDate.now());
        Integer per = SERIE_REPOSITORY.leggTilNyKunde("per", LocalDate.now());
        SERIE_REPOSITORY.leggTilTypeForKunde(trym, Animasjon);
        SERIE_REPOSITORY.leggTilTypeForKunde(lisa, Drama);
        SERIE_REPOSITORY.leggTilTypeForKunde(jens, Reality);
        SERIE_REPOSITORY.leggTilTypeForKunde(per, Animasjon, Drama, TalkShow);

        List<Kunde> kunder = SERIE_REPOSITORY.hentKunderSomLikerSerie(1);
        assertThat(kunder.size()).isEqualTo(3);
        assertThat(kunder.get(trym)
            .getInterestedIn()
            .get(0)
            .equals(Animasjon));
        assertThat(kunder.stream()
            .filter(k -> k.getNavn()
                .equals("trym"))
            .filter(k -> k.getNavn()
                .equals("lisa"))
            .filter(k -> k.getNavn()
                .equals("per")));

    }

    @Test
    public void skalHenteAlleSerierSomHarSammeSerieType() {
        SERIE_REPOSITORY.lagNySerie("James bond", Humor, Drama);
        SERIE_REPOSITORY.lagNySerie("LOTR", Drama, Krim);
        SERIE_REPOSITORY.lagNySerie("Harry Potter", Drama);
        SERIE_REPOSITORY.lagNySerie("Åmot", Krim);

        List<Serie> series = SERIE_REPOSITORY.hentSerierMedSammeType(Drama);
        List<Serie> series1 = SERIE_REPOSITORY.hentSerierMedSammeType(Krim);
        assertThat(series.size()).isEqualTo(3);
        assertThat(series1.size()).isEqualTo(2);
        assertThat(series.stream()
            .filter(k -> k.getType()
                .contains(Drama)));

        assertThat(series1.stream()
            .filter(k -> k.getType()
                .contains(Krim)));

        assertThat(series.get(0)
            .getTitle()).isEqualTo("Harry Potter");

        assertThat(series.stream()
            .filter(s -> s.getTitle()
                .equals("Åmot"))
            .findFirst()
            .isEmpty());

    }

}
