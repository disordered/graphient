package graphient

import TestSchema.Domain._
import org.scalatest._
import sangria.validation.QueryValidator

class QueryGeneratorSpec extends FunSpec with Matchers {

  describe("QueryGenerator") {
    val queryGenerator = QueryGenerator[UserRepo, Unit](TestSchema.schema)

    describe("V2 api") {

      it("should generate a valid query ast for queries") {
        val queryAst   = queryGenerator.generateQuery(QueryV2(TestSchema.Queries.getUser))
        val violations = QueryValidator.default.validateQuery(TestSchema.schema, queryAst)

        violations shouldBe empty
      }

      it("should generate a valid query ast for mutations") {
        val queryAst   = queryGenerator.generateQuery(MutationV2(TestSchema.Mutations.createUser))
        val violations = QueryValidator.default.validateQuery(TestSchema.schema, queryAst)

        violations shouldBe empty
      }

      ignore("should not allow queries to be called as mutations") {
        intercept {
          queryGenerator.generateQuery(QueryV2(TestSchema.Mutations.createUser))
        }
      }

    }

  }

}
