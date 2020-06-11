package cz.sima.msbank

import cz.sima.msbank.feature.cards.CardType
import cz.sima.msbank.utils.Validations
import org.junit.Assert
import org.junit.Test

/**
 * Created by Michal Šíma on 07.06.2020.
 */
class CardModelTest {

    @Test
    fun cardValidationTest() {

        Assert.assertEquals(false, Validations.isCardNumberValid(""))
        Assert.assertEquals(false, Validations.isCardNumberValid("1  2  3  4  5  6"))
        Assert.assertEquals(false, Validations.isCardNumberValid("      1101      "))
        Assert.assertEquals(false, Validations.isCardNumberValid("card number is 5"))
        Assert.assertEquals(false, Validations.isCardNumberValid("12341234a1231234"))
        Assert.assertEquals(true, Validations.isCardNumberValid("4929894357589478"))
        Assert.assertEquals(true, Validations.isCardNumberValid("4485208851861612"))
        Assert.assertEquals(true, Validations.isCardNumberValid("2720999371095595"))
        Assert.assertEquals(false, Validations.isCardNumberValid("2720-999371095595"))
        Assert.assertEquals(false, Validations.isCardNumberValid("2720-99371095595"))
        Assert.assertEquals(true, Validations.isCardNumberValid("2720991345309848"))
        Assert.assertEquals(false, Validations.isCardNumberValid("2720991345309849"))
        Assert.assertEquals(false, Validations.isCardNumberValid("2720991345309847"))
        Assert.assertEquals(true, Validations.isCardNumberValid("2720 9913 4530 9848"))
        Assert.assertEquals(true, Validations.isCardNumberValid("  2 7 20 9913   45 309 84 8"))
    }

    @Test
    fun cardTypeTest() {
        // TODO finish and check tests
        Assert.assertEquals(CardType.fromCardNumber("4929894357589478"), CardType.VISA)
        Assert.assertEquals(CardType.fromCardNumber("4485208851861612"), CardType.VISA)
        Assert.assertEquals(CardType.fromCardNumber("2720999371095595"), CardType.MASTER_CARD)
        Assert.assertEquals(CardType.fromCardNumber("2720991345309848"), CardType.MASTER_CARD)
        Assert.assertEquals(CardType.fromCardNumber("6011139015337157"), CardType.DISCOVER)
        Assert.assertEquals(CardType.fromCardNumber("6011071884045225"), CardType.DISCOVER)
        Assert.assertEquals(CardType.fromCardNumber(""), CardType.UNKNOWN)
        Assert.assertEquals(CardType.fromCardNumber("card number is 5"), CardType.UNKNOWN)
    }
}