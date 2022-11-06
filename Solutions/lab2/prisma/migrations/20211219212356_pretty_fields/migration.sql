/*
  Warnings:

  - Added the required column `prettyDateReleased` to the `Game` table without a default value. This is not possible if the table is not empty.
  - Added the required column `prettyRating` to the `Game` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE "Game" ADD COLUMN     "prettyDateReleased" TEXT NOT NULL,
ADD COLUMN     "prettyRating" TEXT NOT NULL;
