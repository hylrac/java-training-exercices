package io.robusta.hand.solution;

import java.util.Collections;
import java.util.LinkedList;
import java.util.TreeSet;

import io.robusta.hand.Card;
import io.robusta.hand.interfaces.IDeck;

public class Deck extends LinkedList<Card> implements IDeck {

	private static final long serialVersionUID = -4686285366508321800L;

	public Deck() {

	}

	@Override
	public Card pick() {
		Card card;

		Collections.shuffle(this);

		card = this.get(0);
		this.remove(0);
		// shuffle;
		// remove card from deck and returns it
		return card;
	}

	@Override
	public TreeSet<Card> pick(int number) {
		TreeSet<Card> pickedCards = new TreeSet<>();
		for (int i = 0; i < number; i++) {
			pickedCards.add(pick());
		}
		// reuse pick()
		return pickedCards;
	}

	@Override
	public Hand giveHand() {

		TreeSet<Card> handCards = pick(5);
		Hand hand = new Hand(handCards);

		// A hand is a **5** card TreeSet
		return hand;
	}

}
