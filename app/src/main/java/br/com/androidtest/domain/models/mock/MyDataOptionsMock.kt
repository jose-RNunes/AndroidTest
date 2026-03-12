package br.com.androidtest.domain.models.mock

import br.com.androidtest.domain.models.mydata.MyDataAction
import br.com.androidtest.domain.models.mydata.MyDataOptionDomain

object MyDataOptionsMock {

    fun getOptions(): List<MyDataOptionDomain> {
        return listOf(
            MyDataOptionDomain(
                iconUrl = "ic_document",
                title = "Meu plano",
                action = MyDataAction.MY_PLAN
            ),
            MyDataOptionDomain(
                iconUrl = "ic_download",
                title = "Baixar contrato",
                action = MyDataAction.SHARE_CONTRACT
            ),
            MyDataOptionDomain(
                iconType = "ic_message",
                title = "Privacidade",
                action = MyDataAction.WEB,
                externalUrl = "https://www.lipsum.com/"
            ),
            MyDataOptionDomain(
                iconType = "ic_block",
                title = "Sair",
                action = MyDataAction.EXIT
            )
        )
    }
}
