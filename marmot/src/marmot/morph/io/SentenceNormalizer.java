// Copyright 2014 Thomas Müller
// This file is part of MarMoT, which is licensed under GPLv3.

package marmot.morph.io;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import marmot.core.Sequence;
import marmot.core.Token;
import marmot.morph.Sentence;
import marmot.morph.Word;
import marmot.util.CapStats;
import marmot.util.CapStats.CapType;
import marmot.util.StringUtils;
import marmot.util.StringUtils.Mode;

public class SentenceNormalizer {
	
//	public enum Mode {
//		none,
//		bracket,
//		lower,
//		umlaut
//	}

	public static Sequence normalizeSentence(Sequence sentence, Mode mode) {

		if (mode == Mode.none) {
			return sentence;
		}
		
		List<Word> words = new ArrayList<Word>(sentence.size());

		for (Token token : sentence) {
			Word word = (Word) token;
			String form = word.getWordForm();
			CapType type = CapStats.getCapType(form);

			if (type != null && type != CapType.lower && Mode.bracket != mode) {
				String[] features = word.getTokenFeatures();
				String[] new_features;
				if (features == null) {
					new_features = new String[1];
				} else {
					new_features = new String[features.length + 1];
					System.arraycopy(features, 0, new_features, 0,
							features.length);
				}
				new_features[new_features.length - 1] = type.toString();
				word.setTokenFeatures(new_features);
			}

			word.setWordForm(StringUtils.normalize(form, mode));
			words.add(word);
		}

		return new Sentence(words);
	}

	public static Collection<Sequence> normalizeSentences(
			Collection<Sequence> sentences, Mode mode) {

		List<Sequence> list = new ArrayList<Sequence>(sentences.size());

		for (Sequence sequence : sentences) {
			list.add(normalizeSentence(sequence, mode));
		}

		return list;
	}

}
