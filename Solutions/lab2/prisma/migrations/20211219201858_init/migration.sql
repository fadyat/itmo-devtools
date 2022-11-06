-- CreateTable
CREATE TABLE "Game" (
    "id" TEXT NOT NULL,
    "title" TEXT NOT NULL,
    "description" TEXT,
    "image" TEXT NOT NULL,
    "cover" TEXT NOT NULL,
    "rating" INTEGER NOT NULL,
    "url" TEXT NOT NULL,
    "hasDemo" BOOLEAN NOT NULL DEFAULT false,
    "hasDigitalVersion" BOOLEAN NOT NULL DEFAULT true,
    "hasPhysicalVersion" BOOLEAN NOT NULL DEFAULT true,
    "publisher" TEXT NOT NULL,
    "dateReleased" TIMESTAMP(3) NOT NULL,
    "dateUpdated" TIMESTAMP(3),

    CONSTRAINT "Game_pkey" PRIMARY KEY ("id")
);
