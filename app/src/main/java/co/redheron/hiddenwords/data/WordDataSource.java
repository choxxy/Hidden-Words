package co.redheron.hiddenwords.data;

import co.redheron.hiddenwords.model.Word;

import java.util.List;

/**
 * Created by abdularis on 18/07/17.
 */

public interface WordDataSource {
    List<Word> getWords();
}
