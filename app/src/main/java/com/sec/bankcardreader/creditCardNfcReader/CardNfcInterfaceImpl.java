package com.sec.bankcardreader.creditCardNfcReader;

public class CardNfcInterfaceImpl implements CardNfcAsyncTask.CardNfcInterface {
    private CardNfcAsyncTask mCardNfcAsyncTask;

    public CardNfcInterfaceImpl(){
        super();
    }

    public CardNfcInterfaceImpl(CardNfcAsyncTask mCardNfcAsyncTask){
        this.mCardNfcAsyncTask=mCardNfcAsyncTask;
    }
    @Override
    public void startNfcReadCard() {

    }

    @Override
    public void cardIsReadyToRead() {
        String card = mCardNfcAsyncTask.getCardNumber();
        String expiredDate = mCardNfcAsyncTask.getCardExpireDate();
        String cardType = mCardNfcAsyncTask.getCardType();
    }

    @Override
    public void doNotMoveCardSoFast() {

    }

    @Override
    public void unknownEmvCard() {

    }

    @Override
    public void cardWithLockedNfc() {

    }

    @Override
    public void finishNfcReadCard() {

    }
}
