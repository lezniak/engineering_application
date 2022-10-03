//package com.example.apiapp.data.repository
//
//import com.example.apiapp.data.remote.dto.BookDTO
//import com.example.apiapp.data.remote.dto.CharacterDTO
//import javax.inject.Inject
//
//class LotrRepositoryImpl @Inject constructor(
//    private val api: LotrApi
//): LotrRepository{
//    override suspend fun getBooks(): List<BookDTO> {
//        return api.getBooks()
//    }
//
//    override suspend fun getCharacters(): CharacterDTO {
//        return api.getCharacters()
//    }
//
//    override suspend fun getCharacterById(characterId: String): CharacterDTO {
//        return api.getCharacter(characterId)
//    }
//}