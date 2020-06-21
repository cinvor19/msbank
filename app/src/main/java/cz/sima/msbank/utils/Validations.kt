package cz.sima.msbank.utils

import cz.sima.msbank.shared.BankAccount
import cz.sima.msbank.utils.extensions.countDigitRoot

/**
 * Created by Michal Šíma on 07.06.2020.
 */
object Validations {

    const val MAX_PREFIX_LENGTH = 6
    const val MAX_NUMBER_LENGTH = 10
    const val MIN_NUMBER_LENGTH = 2

    val bankCodes = hashMapOf(
        Pair("0100", "Komerční banka, a.s."),
        Pair("0300", "ČSOB, a.s."),
        Pair("0600", "MONETA Money Bank, a.s."),
        Pair("0666", "MS Bank, a.s."),
        Pair("0710", "Česká národní banka"),
        Pair("0800", "Česká spořitelna, a.s."),
        Pair("2010", "Fio banka, a.s."),
        Pair("2020", "MUFG Bank (Europe) N.V. Prague Branch"),
        Pair("2030", "AKCENTA, spořitelní a úvěrní družstvo"),
        Pair("2060", "Citfin, spořitelní družstvo"),
        Pair("2070", "Moravský Peněžní Ústav – spořitelní družstvo"),
        Pair("2100", "Hypoteční banka, a.s."),
        Pair("2200", "Peněžní dům, spořitelní družstvo"),
        Pair("2220", "Artesa, spořitelní družstvo"),
        Pair("2240", "Poštová banka, a.s., pobočka Česká republika"),
        Pair("2250", "Banka CREDITAS a.s."),
        Pair("2260", "ANO spořitelní družstvo"),
        Pair("2310", "ZUNO BANK AG, organizační složka"),
        Pair("2600", "Citibank Europe plc, organizační složka"),
        Pair("2700", "UniCredit Bank Czech Republic and Slovakia, a.s."),
        Pair("3030", "Air Bank a. s."),
        Pair("3050", "BNP Paribas Personal Finance SA, odštěpný závod"),
        Pair("3060", "PKO BP S.A., Czech Branch"),
        Pair("3500", "ING Bank N.V."),
        Pair("4000", "Expobank CZ a.s."),
        Pair("4300", "Českomoravská záruční a rozvojová banka, a.s."),
        Pair("5500", "Raiffeisenbank, a.s."),
        Pair("5800", "J&T Banka, a.s."),
        Pair("6000", "PPF banka a.s."),
        Pair("6100", "Equa bank a. s."),
        Pair("6200", "COMMERZBANK Aktiengesellschaft, pobočka Praha"),
        Pair("6210", "mBank S.A., organizační složka"),
        Pair("6300", "BNP Paribas Fortis SA/NV, pobočka Česká republika"),
        Pair("6700", "Všeobecná úverová banka a.s., pobočka Praha"),
        Pair("6800", "Sberbank CZ, a.s."),
        Pair("7910", "Deutsche Bank A.G. Filiale Prag"),
        Pair("7940", "Waldviertler Sparkasse Bank AG"),
        Pair("7950", "Raiffeisen stavební spořitelna a.s."),
        Pair("7960", "Českomoravská stavební spořitelna a. s."),
        Pair("7970", "Wüstenrot - stavební spořitelna a.s."),
        Pair("7980", "Wüstenrot hypoteční banka, a.s."),
        Pair("7990", "Modrá pyramida stavební spořitelna, a.s."),
        Pair("8030", "Raiffeisenbank im Stiftland Waldsassen eG pobočka Cheb, odštěpný závod"),
        Pair("8040", "Oberbank AG pobočka Česká republika"),
        Pair("8060", "Stavební spořitelna České spořitelny, a.s."),
        Pair("8090", "Česká exportní banka, a.s."),
        Pair("8150", "HSBC Bank plc - pobočka Praha"),
        Pair("8200", "PRIVAT BANK AG der Raiffeisenlandesbank Oberösterreich v České republice"),
        Pair("8220", "Payment Execution s.r.o."),
        Pair("8230", "EEPAYS s. r. o."),
        Pair("8240", "Družstevní záložna Kredit"),
        Pair("8250", "Bank of China (Hungary) Close Ltd. Prague branch, odštěpný závod")
    )

    fun isCardNumberValid(cardNumber: String): Boolean {
        val numberWithoutSpaces = cardNumber.replace(Regex("\\s+"), "")
        if (numberWithoutSpaces.length != 16) {
            // Card length not valid
            return false
        }

        val chars = numberWithoutSpaces.toCharArray()
        val cardDigits = chars.map(Character::getNumericValue)


        if (containsNonDigits(cardDigits)) {
            // Card number containing non digit
            return false
        }


        val evenIndexDigits = cardDigits.filterIndexed { index, _ -> index % 2 == 0 }
        val oddIndexDigits = cardDigits.filterIndexed { index, _ -> index % 2 == 1 }

        var digitsSum = evenIndexDigits
            .map { (it * 2).countDigitRoot() }
            .sum()

        digitsSum += oddIndexDigits
            .sum()

        return digitsSum % 10 == 0
    }

    fun isBankAccountValid(bankAccount: BankAccount): Boolean {
        val bank = bankAccount.toInstanceWithZeros()
        val weights = listOf(6, 3, 7, 9, 10, 5, 8, 4, 2, 1).reversed()
        val prefixDigits = bank.prefix.toCharArray().map(Character::getNumericValue)
        val numberDigits = bank.number.toCharArray().map(Character::getNumericValue)

        if (containsNonDigits(prefixDigits) || containsNonDigits(numberDigits)) {
            // Card number containing non digit
            return false
        }

        if (prefixDigits.size > MAX_PREFIX_LENGTH) {
            return false
        }

        if (numberDigits.size !in MIN_NUMBER_LENGTH..MAX_NUMBER_LENGTH) {
            return false
        }

        if (!bankCodes.containsKey(bank.bankCode)) {
            return false
        }

        val sumPrefix = prefixDigits
            .reversed()
            .foldIndexed(0) { index, acc, i ->
                acc + i * weights[index]
            }

        val sumNumber = numberDigits
            .reversed()
            .foldIndexed(0) { index, acc, i ->
                acc + i * weights[index]
            }

        return sumPrefix % 11 == 0 && sumNumber % 11 == 0
    }

    private fun containsNonDigits(list: List<Int>): Boolean {
        // If containing -1 or -2 -> number contains non digit char (see Character::getNumericValue)
        return list.containsAll(listOf(-1, -2))
    }
}
