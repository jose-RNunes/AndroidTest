package br.com.androidtest.factory

import br.com.androidtest.data.model.ExitModalDto
import br.com.androidtest.data.model.ModalButtonDto
import br.com.androidtest.data.model.MyDataOptionDto
import br.com.androidtest.data.model.NPMyDataResponseDto
import br.com.androidtest.data.model.NPMyDataScreenDto
import br.com.androidtest.data.model.NPMyPlanExtraPlayDto
import br.com.androidtest.data.model.NPMyPlanHeaderCellDto
import br.com.androidtest.data.model.NPMyPlanHeaderValueDto
import br.com.androidtest.data.model.NPMyPlanResponseDto
import br.com.androidtest.data.model.NPMyPlanScreenDto
import br.com.androidtest.data.model.PlanAppDto
import br.com.androidtest.data.model.RWMyDataContentDto
import br.com.androidtest.data.model.RWMyDataResponseDto
import br.com.androidtest.data.model.RWMyPlanContentDto
import br.com.androidtest.data.model.RWMyPlanResponseDto
import br.com.androidtest.data.model.UserDto
import br.com.androidtest.domain.model.ExitDialogConfig
import br.com.androidtest.domain.model.ExtraApp
import br.com.androidtest.domain.model.MyDataAction
import br.com.androidtest.domain.model.MyDataActionType
import br.com.androidtest.domain.model.MyDataScreen
import br.com.androidtest.domain.model.MyPlanScreen
import br.com.androidtest.domain.model.PlanHeader
import br.com.androidtest.domain.model.PlanQuota
import br.com.androidtest.domain.model.User

object DataFactory {

    val myDataScreen = MyDataScreen(
        title = "Meus dados",
        user = User(
            id = "1",
            name = "Ze",
            documentNumber = "000.000.000-00",
            age = "34",
            avatarUrl = "https://avatar.url",
        ),
        actions = listOf(
            MyDataAction(
                type = MyDataActionType.MY_PLAN,
                title = "Meu plano",
                iconKey = "ic_myplan",
                assetName = null,
                url = null,
                exitDialogConfig = null,
            ),
            MyDataAction(
                type = MyDataActionType.EXIT,
                title = "Sair",
                iconKey = "ic_block",
                assetName = null,
                url = null,
                exitDialogConfig = ExitDialogConfig(
                    title = "Sair?",
                    subtitle = "Deseja realmente sair?",
                    confirmLabel = "Sim",
                    cancelLabel = "Nao",
                ),
            ),
        ),
    )

    val myPlanScreen = MyPlanScreen(
        header = PlanHeader(
            status = "Ativo",
            phoneNumber = "(11) 99999-9999",
            planValue = "R$ 39,99",
        ),
        quota = PlanQuota(planGb = 15, bonusGb = 5),
        extraApps = listOf(ExtraApp(name = "youtube", iconUrl = "https://icon.url")),
    )


    val npMyDataResponseDto = NPMyDataResponseDto(
        screen = NPMyDataScreenDto(
            title = "Meus dados",
            profile = UserDto(
                id = "1",
                name = "Ze",
                age = "34",
                documentNumber = "000.000.000-00",
                avatarUrl = "https://avatar.url",
            ),
            options = listOf(
                MyDataOptionDto(
                    action = "MY_PLAN",
                    title = "Meu plano",
                    iconUrl = "ic_myplan",
                    iconType = null,
                    assetName = null,
                    url = null,
                    modal = null,
                ),
                MyDataOptionDto(
                    action = "EXIT",
                    title = "Sair",
                    iconUrl = "ic_block",
                    iconType = null,
                    assetName = null,
                    url = null,
                    modal = ExitModalDto(
                        title = "Sair?",
                        subtitle = "Deseja realmente sair?",
                        buttons = listOf(
                            ModalButtonDto(title = "Sim", action = "CONFIRM"),
                            ModalButtonDto(title = "Nao", action = "CANCEL"),
                        ),
                    ),
                ),
            ),
        ),
    )

    val rwMyDataResponseDto = RWMyDataResponseDto(
        content = RWMyDataContentDto(
            user = UserDto(
                id = "1",
                name = "Ze",
                age = "34",
                documentNumber = "000.000.000-00",
                avatarUrl = "https://avatar.url",
            ),
        ),
    )

    val npMyPlanResponseDto = NPMyPlanResponseDto(
        screen = NPMyPlanScreenDto(
            name = "Claro flex",
            offerDisplay = null,
            planValue = "R$ 39,99",
            header = listOf(
                listOf(
                    NPMyPlanHeaderCellDto(
                        title = "Status",
                        value = NPMyPlanHeaderValueDto(text = "Ativo", alignment = null, textColor = null),
                    ),
                    NPMyPlanHeaderCellDto(
                        title = "Numero",
                        value = NPMyPlanHeaderValueDto(text = "(11) 99999-9999", alignment = null, textColor = null),
                    ),
                ),
                listOf(
                    NPMyPlanHeaderCellDto(
                        title = "Plano",
                        value = NPMyPlanHeaderValueDto(text = "15GB", alignment = null, textColor = null),
                    ),
                    NPMyPlanHeaderCellDto(
                        title = "Bonus",
                        value = NPMyPlanHeaderValueDto(text = "5GB", alignment = null, textColor = null),
                    ),
                ),
            ),
            extraPlay = NPMyPlanExtraPlayDto(
                title = "Apps inclusos",
                options = listOf(PlanAppDto(name = "youtube", iconUrl = "https://icon.url")),
            ),
        ),
    )

    val rwMyPlanResponseDto = RWMyPlanResponseDto(
        content = RWMyPlanContentDto(
            status = "Ativo",
            phoneNumber = "(11) 99999-9999",
            planValue = "R$ 39,99",
            plan = 15,
            bonus = 5,
            extraPlayBaseUrl = "https://icons.url",
            extraPlay = "youtube.png;",
        ),
    )
}


