package cz.sima.msbank

import cz.sima.msbank.shared.BankAccount
import cz.sima.msbank.utils.Validations
import org.junit.Assert
import org.junit.Test

/**
 * Created by Michal Šíma on 11.06.2020.
 */

class BankAccountTest {

    @Test
    fun validateBankAccountTest() {
        Assert.assertEquals(
            Validations.isBankAccountValid(
                BankAccount(
                    number = "2171532",
                    bankCode = "0800"
                )
            ), true
        )
        Assert.assertEquals(
            Validations.isBankAccountValid(
                BankAccount(
                    number = "1265098001",
                    bankCode = "5500"
                )
            ), true
        )
        Assert.assertEquals(
            Validations.isBankAccountValid(
                BankAccount(
                    number = "188505042",
                    bankCode = "0300"
                )
            ), true
        )
        Assert.assertEquals(
            Validations.isBankAccountValid(
                BankAccount(
                    prefix = "35",
                    number = "3355550267",
                    bankCode = "0100"
                )
            ), true
        )
        Assert.assertEquals(
            Validations.isBankAccountValid(
                BankAccount(
                    number = "2064330104",
                    bankCode = "2600"
                )
            ), true
        )
        Assert.assertEquals(
            Validations.isBankAccountValid(
                BankAccount(
                    number = "5276368001",
                    bankCode = "5500"
                )
            ), true
        )
        Assert.assertEquals(
            Validations.isBankAccountValid(
                BankAccount(
                    number = "233127256",
                    bankCode = "0300"
                )
            ), true
        )
        Assert.assertEquals(
            Validations.isBankAccountValid(
                BankAccount(
                    number = "2030405032",
                    bankCode = "0800"
                )
            ), true
        )
        // Slovak bank
        Assert.assertEquals(
            Validations.isBankAccountValid(
                BankAccount(
                    number = "2942462814",
                    bankCode = "1100"
                )
            ), false
        )
        Assert.assertEquals(
            Validations.isBankAccountValid(
                BankAccount(
                    number = "2600858114",
                    bankCode = "2010"
                )
            ), true
        )
        Assert.assertEquals(
            Validations.isBankAccountValid(
                BankAccount(
                    number = "2200858115",
                    bankCode = "2010"
                )
            ), true
        )
        Assert.assertEquals(
            Validations.isBankAccountValid(
                BankAccount(
                    number = "1199210244",
                    bankCode = "0600"
                )
            ), true
        )
    }
}