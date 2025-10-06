package pgkn.model

case class KaptenAllocEntry(
    entryType: String,
    time: BigInt,
    group: String,
    room: String,
    supervisor: String
)
