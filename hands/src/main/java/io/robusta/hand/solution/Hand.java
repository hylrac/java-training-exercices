package io.robusta.hand.solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import io.robusta.hand.Card;
import io.robusta.hand.HandClassifier;
import io.robusta.hand.HandValue;
import io.robusta.hand.interfaces.IDeck;
import io.robusta.hand.interfaces.IHand;
import io.robusta.hand.interfaces.IHandResolver;

public class Hand extends TreeSet<Card> implements IHand {

	private static final long serialVersionUID = 7824823998655881611L;

	public Hand(TreeSet<Card> handCards) {
		for (Card element : handCards) {

			this.add(element);
		}
	}

	public Hand() {
	}

	public Set<Card> changeCards(IDeck deck, Set<Card> cards) {
		// For exemple remove three cards from this hand
		// , and get 3 new ones from the Deck
		// returns the new given cards
		return null;
	}

	/**
	 * beats is the same than compareTo, but with a nicer name. The problem is
	 * that it does not handle equality :(
	 * 
	 * @param villain
	 * @return
	 */
	@Override
	public boolean beats(IHand villain) {
		HandValue handValue = this.getValue();

		// if (this.getClassifier().compareTo(villain.getClassifier())) return
		// true;
		if (handValue.compareTo(villain.getValue()) > 0)
			return true;
		return false;

	}

	@Override
	public IHand getHand() {
		return this;

	}

	@Override
	public HandClassifier getClassifier() {

		return this.getValue().getClassifier();
	}

	/**
	 * Returns number of identical cards 5s5cAd2s3s has two cardValue of 5
	 */
	@Override
	public int number(int cardValue) {
		int result = 0;
		for (Card current : this) {
			if (current.getValue() == cardValue) {
				result++;
			}
		}
		return result;
	}

	/**
	 * The fundamental map Check the tests and README to understand
	 */
	@Override
	public Map<Integer, List<Card>> group() {
		HashMap<Integer, List<Card>> map = new HashMap<>();
		for (int i = 0; i <= 14; i++) {
			ArrayList<Card> listCards = new ArrayList<>();

			for (Card card : this) {
				if (card.getValue() == i)
					listCards.add(card);
			}

			// System.out.println(listCards.toString());
			if (listCards.size() != 0)
				map.put(i, listCards);

		}

		// fill the map
		// System.out.println(map.toString());
		return map;
	}

	// different states of the hand
	// Using stateful variables. We need to fill this, then use it before.
	int levelValue = 0;
	// Needed with two pairs or full
	int secondValue = 0;
	// Put all cards for flush or highCard ;
	TreeSet<Card> singleCards = new TreeSet<>();

	/**
	 * return all single cards not used to build the classifier
	 *
	 * @param map
	 * @return
	 */
	TreeSet<Card> getSingleCards() {
		// method is done, DO NOT TOUCH !
		TreeSet<Card> singleCards = new TreeSet<>();
		// if straight or flush : return empty
		System.out.println("TU PASSES ICI?????");
		if ((this.getClassifier() == HandClassifier.FLUSH) || (this.getClassifier() == HandClassifier.STRAIGHT)
				|| (this.getClassifier() == HandClassifier.STRAIGHT_FLUSH)
				|| (this.getClassifier() == HandClassifier.FULL)) {
			System.out.println("ET ICI?????");
			return singleCards;
		}
		// If High card, return 4 cards

		if (this.getClassifier() == HandClassifier.HIGH_CARD) {
			System.out.println("OU LA ?");
			for (int i = 0; i < this.highestValue(); i++) {
				singleCards.addAll(this.group().get(i));

			}
			return singleCards;

		}
		System.out.println("est ce que je passe ici ? ");
		for (List<Card> cards : this.group().values()) {
			if (cards.size() == 1) {
				singleCards.add(cards.get(0));
			}
		}
		return singleCards;
	}

	@Override
	public boolean isPair() {
		int count = 0;
		int bufferLevelValue = 0;

		for (int i = 2; i < 15; i++) {
			if (this.group().get(i) != null) {
				if (this.group().get(i).size() == 2) {
					bufferLevelValue = i;
					count++;
				}
			}
		}

		if (count == 1) {
			this.levelValue = bufferLevelValue;
			TreeSet<Card> singleCards = new TreeSet<>();
			for (List<Card> cards : this.group().values()) {
				if (cards.size() == 1) {
					singleCards.add(cards.get(0));
				}
			}
			this.singleCards = singleCards;

			return true;
		} else {
			return false;

		}
	}

	@Override
	public boolean isDoublePair() {
		int count = 0;
		int bufferLevelValue = 0;
		for (int i = 2; i < 15; i++) {
			if (this.group().get(i) != null) {
				if (this.group().get(i).size() == 2) {
					bufferLevelValue = i;
					count++;

				}

			}
		}
		if (count == 2) {
			this.levelValue = bufferLevelValue;
			TreeSet<Card> singleCards = new TreeSet<>();
			for (List<Card> cards : this.group().values()) {
				if (cards.size() == 1) {
					singleCards.add(cards.get(0));
				}
			}
			this.singleCards = singleCards;
			return true;
		} else {
			return false;

		}
	}

	@Override
	public boolean isHighCard() {
		if ((!this.isDoublePair()) && (!this.isFlush()) && (!this.isFourOfAKind()) && (!this.isFull())
				&& (!this.isPair()) && (!this.isStraight()) && (!this.isStraightFlush())) {
			TreeSet<Card> singleCards = new TreeSet<>();
			for (int i = 0; i < this.highestValue(); i++) {
				singleCards.addAll(this.group().get(i));

			}
			this.singleCards = singleCards;

			return true;
		} else {
			return false;

		}
	}

	@Override
	public boolean isTrips() {
		int count = 0;
		int bufferLevelValue = 0;
		for (int i = 2; i < 15; i++) {
			if (this.group().get(i) != null) {
				if (this.group().get(i).size() == 3) {

					bufferLevelValue = i;
					count++;
				}

			}
		}
		if (count == 1) {
			TreeSet<Card> singleCards = new TreeSet<>();
			for (List<Card> cards : this.group().values()) {
				if (cards.size() == 1) {
					singleCards.add(cards.get(0));
				}
			}
			this.singleCards = singleCards;
			this.levelValue = bufferLevelValue;
			return true;
		} else {
			return false;

		}

	}

	@Override
	public boolean isFourOfAKind() {
		int count = 0;
		int bufferLevelValue = 0;
		for (int i = 2; i < 15; i++) {
			if (this.group().get(i) != null) {
				if (this.group().get(i).size() == 4) {
					bufferLevelValue = i;
					count++;
				}

			}
		}
		if (count == 1) {
			TreeSet<Card> singleCards = new TreeSet<>();
			for (List<Card> cards : this.group().values()) {
				if (cards.size() == 1) {
					singleCards.add(cards.get(0));
				}
			}
			this.singleCards = singleCards;
			this.levelValue = bufferLevelValue;
			return true;
		} else {
			return false;

		}

	}

	@Override
	public boolean isFull() {
		if (this.isTrips() && this.isPair()) {

			return true;
		} else {
			return false;

		}
	}

	@Override
	public boolean isStraight() {
		// tentative ratée pour copier group().get(14) dans group.get(1)
		// List<Card> listAs = this.group().get(14);
		// this.group().put(1, listAs);

		for (int i = 1; i < 11; i++) {
			int count = 0;
			int j = i;
			while (j < i + 5) {
				if (j == 1) {
					if (this.group().get(14) != null) {
						count++;
					}
				}
				if (this.group().get(j) != null) {
					count++;
				}
				j++;
			}
			if (count == 5) {

				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isFlush() {

		for (int i = 1; i < 5; i++) {
			int count = 0;
			for (Card element : this) {

				if (element.getColor().getValue() == i)
					count++;
				if (count == 5) {

					return true;
				}
			}

		}

		return false;
	}

	@Override
	public boolean isStraightFlush() {
		if (this.isStraight() && this.isFlush()) {

			return true;
		} else {
			return false;
		}

	}

	@Override
	public HandValue getValue() {
		HandValue handValue = new HandValue();

		// Exemple for FourOfAKind ; // do for all classifiers
		if (this.isFourOfAKind()) {
			handValue.setClassifier(HandClassifier.FOUR_OF_A_KIND);
			handValue.setLevelValue(this.levelValue);
			handValue.setSingleCards(this.singleCards); // or
														// this.getsingleCards()
			return handValue;
		}

		if (this.isStraightFlush()) {
			handValue.setClassifier(HandClassifier.STRAIGHT_FLUSH);
			handValue.setLevelValue(this.levelValue);
			handValue.setSingleCards(this.singleCards); // or
														// this.getsingleCards()
			return handValue;
		}

		if (this.isDoublePair() && (!isFourOfAKind())) {
			handValue.setClassifier(HandClassifier.TWO_PAIR);
			handValue.setLevelValue(this.levelValue);
			handValue.setSingleCards(this.singleCards); // or
														// this.getsingleCards()
			return handValue;
		}

		if (this.isPair() && (!isFourOfAKind())) {
			handValue.setClassifier(HandClassifier.PAIR);
			handValue.setLevelValue(this.levelValue);
			handValue.setSingleCards(this.singleCards); // or
														// this.getsingleCards()
			return handValue;
		}

		if (this.isStraight() && (!isStraightFlush())) {
			handValue.setClassifier(HandClassifier.STRAIGHT);
			handValue.setLevelValue(this.levelValue);
			handValue.setSingleCards(this.singleCards); // or
														// this.getsingleCards()
			return handValue;
		}

		if (this.isFlush() && (!isStraightFlush())) {
			handValue.setClassifier(HandClassifier.FLUSH);
			handValue.setLevelValue(this.levelValue);
			handValue.setSingleCards(this.singleCards); // or
														// this.getsingleCards()
			return handValue;
		}

		if (this.isFourOfAKind()) {
			handValue.setClassifier(HandClassifier.FOUR_OF_A_KIND);
			handValue.setLevelValue(this.levelValue);
			handValue.setSingleCards(this.singleCards); // or
														// this.getsingleCards()
			return handValue;
		}

		if (this.isTrips() && (!isFull()) && (!isFourOfAKind())) {
			handValue.setClassifier(HandClassifier.TRIPS);
			handValue.setLevelValue(this.levelValue);
			handValue.setSingleCards(this.singleCards); // or
														// this.getsingleCards()
			return handValue;
		}

		if (this.isFull()) {
			handValue.setClassifier(HandClassifier.FULL);
			handValue.setLevelValue(this.levelValue);
			handValue.setSingleCards(this.singleCards); // or
														// this.getsingleCards()
			return handValue;
		}

		if (this.isHighCard()) {
			handValue.setClassifier(HandClassifier.HIGH_CARD);
			handValue.setLevelValue(this.levelValue);
			handValue.setSingleCards(this.singleCards); // or
														// this.getsingleCards()
			return handValue;
		}

		// For the flush, all singleCards are needed

		return handValue;
	}

	@Override
	public boolean hasCardValue(int level) {
		if (this.group().get(level)!=null){
			return true;
		}else{
			return false;
			
		}

	}

	@Override
	public boolean hasAce() {
		if (this.group().get(14)!=null){
			return true;
		}else{
			return false;
			
		}
	}

	@Override
	public int highestValue() {
		int buffer = 0;

		/*
		 * if (!
		 * (this.group().get(2)!=null)&&(this.group().get(14)!=null)&&(this.
		 * isStraight()||this.isStraightFlush())){ for (int i = 2;i<15;i++){ if
		 * (this.group().get(i)!=null) buffer =i; } return buffer; }else return
		 * 5;
		 */
		if ((this.group().get(2) != null) && (this.group().get(14) != null)
				&& (this.isStraight() || this.isStraightFlush())) {
			return 5;
		} else {
			for (int i = 2; i < 15; i++) {
				if (this.group().get(i) != null)
					buffer = i;
			}
			return buffer;
		}
	}

	@Override
	public int compareTo(IHandResolver o) {
		// You should reuse HandValue.compareTo()
		return 0;
	}

}
